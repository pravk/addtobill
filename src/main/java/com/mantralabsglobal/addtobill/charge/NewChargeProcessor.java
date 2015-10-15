package com.mantralabsglobal.addtobill.charge;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.requestModel.NewChargeRequest;

@Component(value="newChargeProcessor")
public class NewChargeProcessor extends ChargeProcessor<NewChargeRequest>{

	
	@Override
	protected Map<Account, Transaction> createTransactions(Charge charge) {
		Map<Account, Transaction> transactionList = new HashMap<>();
		
		Account account = accountRepository.findOneByOwnerIdAndCurrency(charge.getMerchantId(),charge.getCurrency());
		
		Transaction userTransaction = new Transaction();
		userTransaction.setAmount(charge.getAmount() - charge.getApplicationFee());
		userTransaction.setCurrency(charge.getCurrency());
		userTransaction.setChargeId(charge.getChargeId());
		userTransaction.setDebitCreditIndicator(Transaction.CREDIT);
		userTransaction.setTransactionAccountId(account.getAccountId());
		transactionList.put(account, userTransaction);
		
		account = accountRepository.findOneByOwnerIdAndCurrency(charge.getUserId(),charge.getCurrency());
		
		Transaction transaction = new Transaction();
		transaction.setAmount(charge.getAmount());
		transaction.setCurrency(charge.getCurrency());
		transaction.setChargeId(charge.getChargeId());
		transaction.setDebitCreditIndicator(Transaction.DEBIT);
		transaction.setTransactionAccountId(account.getAccountId());
		transactionList.put(account, transaction);
		
		return transactionList;
	}
	
	@Override
	protected Charge createCharge(NewChargeRequest chargeRequest) {
		return ChargeBuilder.newCharge()
				.setAmount(chargeRequest.getAmount(), chargeRequest.getCurrency())
				.setUser(chargeRequest.getUserId())
				.setMerchantId(chargeRequest.getMerchantId())
				.setDescription(chargeRequest.getDescription())
				.setStatementDescription(chargeRequest.getStatementDescription())
				.setChargeDate(chargeRequest.getChargeDate())
				.setStatus(Charge.CHARGE_STATUS_RECORDED)
				.setApplicationFees(0)
				.build();
	}


	
}
