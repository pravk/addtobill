package com.mantralabsglobal.addtobill.charge;

import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.model.AccountBalance;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.model.MerchantAccount;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.UserAccount;
import com.mantralabsglobal.addtobill.requestModel.NewChargeRequest;

@Component(value="newChargeProcessor")
public class NewChargeProcessor extends ChargeProcessor<NewChargeRequest>{

	@Override
	public Charge processRequest(NewChargeRequest chargeRequest) throws Exception {
		Charge charge = ChargeBuilder.newCharge()
				.setAmount(chargeRequest.getAmount(), chargeRequest.getCurrency())
				.setUser(chargeRequest.getUserId())
				.setMerchantId(chargeRequest.getMerchantId())
				.setDescription(chargeRequest.getDescription())
				.setStatementDescription(chargeRequest.getStatementDescription())
				.setChargeDate(chargeRequest.getChargeDate())
				.setApplicationFees(0)
				.build();

		charge = chargeRepository.save(charge);
		return processNewCharge(charge);
	}
	
	private Charge processNewCharge(Charge charge) throws InsufficientBalanceException{
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
		MerchantAccount merchantAccount = merchantAccountRepository.findOneByMerchantIdAndCurrency(charge.getMerchantId(),charge.getCurrency());
		
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
		return chargeRepository.save(charge);
	}

	
}
