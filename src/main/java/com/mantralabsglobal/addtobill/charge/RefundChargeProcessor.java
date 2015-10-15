package com.mantralabsglobal.addtobill.charge;

import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.model.AccountBalance;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.model.MerchantAccount;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.UserAccount;
import com.mantralabsglobal.addtobill.requestModel.CancelChargeRequest;

@Component(value="refundChargeProcessor")
public class RefundChargeProcessor extends ChargeProcessor<CancelChargeRequest> {

	@Override
	public Charge processRequest(CancelChargeRequest request) throws Exception {
		Charge charge = chargeRepository.findOne(request.getChargeId());
		Charge refundCharge = ChargeBuilder.newRefundCharge(charge)
			.setDescription(request.getDescription())
			.setStatementDescription(request.getStatementDescription())
			.build();
		
		refundCharge= chargeRepository.save(refundCharge);
		
		charge = chargeRepository.save(charge);
		return processRefund(charge);
	}
	
	private Charge processRefund(Charge charge) {
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
		MerchantAccount merchantAccount = merchantAccountRepository.findOneByMerchantIdAndCurrency(charge.getMerchantId(),charge.getCurrency());
				
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
		return chargeRepository.save(charge);
	}

	
}
