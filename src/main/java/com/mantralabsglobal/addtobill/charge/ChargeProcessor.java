package com.mantralabsglobal.addtobill.charge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.model.AccountBalance;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.model.MerchantAccount;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.UserAccount;
import com.mantralabsglobal.addtobill.repository.AccountRepository;
import com.mantralabsglobal.addtobill.repository.ChargeRepository;
import com.mantralabsglobal.addtobill.repository.MerchantAccountRepository;
import com.mantralabsglobal.addtobill.repository.TransactionRepository;

@Component
public class ChargeProcessor {

	@Autowired
	private AccountRepository userAccountRepository;
	
	@Autowired
	private MerchantAccountRepository merchantAccountRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private ChargeRepository chargeRepository;
	
	@Subscribe
	public void process(Charge charge) throws InsufficientBalanceException{
		switch(charge.getChargeType()){
		
			case Charge.CHARGE_TYPE_CHARGE:
				processNewCharge(charge);
				break;
			case Charge.CHARGE_TYPE_REFUND:
				processRefund(charge);
				break;
		
		}
		
	}
	
	private void processRefund(Charge charge) {
		//Get User Account
		UserAccount userAccount = userAccountRepository.findOneByUserIdAndCurrency(charge.getUserId(), charge.getCurrency());
		
		//Create User Transaction
		Transaction userTransaction = new Transaction();
		userTransaction.setAmount(charge.getAmount());
		userTransaction.setCurrency(charge.getCurrency());
		userTransaction.setChargeId(charge.getChargeId());
		userTransaction.setDebitCreditIndicator(Transaction.CREDIT);
		userTransaction.setTransactionAccountId(userAccount.getAccountId());
		userTransaction = transactionRepository.save(userTransaction);
		
		//Update user account balance
		userAccount.setRemainingCreditBalance(userAccount.getRemainingCreditBalance()+userTransaction.getAmount());
		userAccountRepository.save(userAccount);
		
		//Get Merchant Account
		MerchantAccount merchantAccount = merchantAccountRepository.findOne(charge.getMerchantAccountId());
		
		Charge originalCharge = chargeRepository.findOne(charge.getLinkedChargeId());
		Transaction originalMerchantTransaction = transactionRepository.findOneByChargeIdAndTransactionAccountId(originalCharge.getChargeId(), merchantAccount.getMerchantAccountId());
		
		//Create Merchant Transaction
		Transaction merchantTransaction = new Transaction();
		merchantTransaction.setAmount(originalMerchantTransaction.getAmount());
		merchantTransaction.setCurrency(charge.getCurrency());
		merchantTransaction.setChargeId(charge.getChargeId());
		merchantTransaction.setDebitCreditIndicator(Transaction.DEBIT);
		merchantTransaction.setTransactionAccountId(merchantAccount.getMerchantAccountId());
		merchantTransaction = transactionRepository.save(merchantTransaction);
		
		//Update Merchant Balance
		AccountBalance merchantActBalance = merchantAccount.getAccountBalance();
		merchantActBalance.setPendingBalance(merchantActBalance.getPendingBalance() - merchantTransaction.getAmount());
		merchantAccountRepository.save(merchantAccount);
		
		//Update Charge Object
		charge.setStatus(Charge.CHARGE_STATUS_SUCCESS);
		chargeRepository.save(charge);
	}

	private void processNewCharge(Charge charge) throws InsufficientBalanceException{
		//Get User Account
		UserAccount userAccount = userAccountRepository.findOneByUserIdAndCurrency(charge.getUserId(), charge.getCurrency());
		
		//Validate Account Balance
		
		if(userAccount.getRemainingCreditBalance()<charge.getAmount())
		{
			//Update Charge Object
			charge.setStatus(Charge.CHARGE_STATUS_FAILED);
			chargeRepository.save(charge);
			throw new InsufficientBalanceException();
		}
		
		//Create User Transaction
		Transaction userTransaction = new Transaction();
		userTransaction.setAmount(charge.getAmount());
		userTransaction.setCurrency(charge.getCurrency());
		userTransaction.setChargeId(charge.getChargeId());
		userTransaction.setDebitCreditIndicator(Transaction.DEBIT);
		userTransaction.setTransactionAccountId(userAccount.getAccountId());
		userTransaction = transactionRepository.save(userTransaction);
		
		//Update user account balance
		userAccount.setRemainingCreditBalance(userAccount.getRemainingCreditBalance()-userTransaction.getAmount());
		userAccountRepository.save(userAccount);
		
		//Get Merchant Account
		MerchantAccount merchantAccount = merchantAccountRepository.findOne(charge.getMerchantAccountId());
		
		//Create Merchant Transaction
		Transaction merchantTransaction = new Transaction();
		merchantTransaction.setAmount(charge.getAmount() - charge.getApplicationFee());
		merchantTransaction.setCurrency(charge.getCurrency());
		merchantTransaction.setChargeId(charge.getChargeId());
		merchantTransaction.setDebitCreditIndicator(Transaction.CREDIT);
		merchantTransaction.setTransactionAccountId(merchantAccount.getMerchantAccountId());
		merchantTransaction = transactionRepository.save(merchantTransaction);
		
		//Update Merchant Balance
		AccountBalance merchantActBalance = merchantAccount.getAccountBalance();
		merchantActBalance.setPendingBalance(merchantActBalance.getPendingBalance() + merchantTransaction.getAmount());
		merchantAccountRepository.save(merchantAccount);
		
		//Update Charge Object
		charge.setStatus(Charge.CHARGE_STATUS_SUCCESS);
		chargeRepository.save(charge);
	}

	
}
