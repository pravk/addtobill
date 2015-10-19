package com.mantralabsglobal.addtobill.charge;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.requestModel.CancelChargeRequest;

@Component(value="refundChargeProcessor")
public class RefundChargeProcessor extends ChargeProcessor<CancelChargeRequest> {

	@Override
	protected Map<Account, Transaction> createTransactions(Charge charge) {
		Map<Account, Transaction> transactionList = new HashMap<>();
		
		Account account = accountRepository.findOneByOwnerIdAndCurrency(charge.getUserId(),charge.getCurrency());
		
		Transaction creditTransaction = new Transaction();
		creditTransaction.setAmount(charge.getAmount());
		creditTransaction.setCurrency(charge.getCurrency());
		creditTransaction.setChargeId(charge.getChargeId());
		creditTransaction.setDebitCreditIndicator(Transaction.CREDIT);
		creditTransaction.setTransactionAccountId(account.getAccountId());
		transactionList.put(account, creditTransaction);
		
		account = accountRepository.findOneByOwnerIdAndCurrency(charge.getMerchantId(),charge.getCurrency());
		
		Transaction debitTransaction = new Transaction();
		debitTransaction.setAmount(charge.getAmount());
		debitTransaction.setCurrency(charge.getCurrency());
		debitTransaction.setChargeId(charge.getChargeId());
		debitTransaction.setDebitCreditIndicator(Transaction.DEBIT);
		debitTransaction.setTransactionAccountId(account.getAccountId());
		transactionList.put(account, debitTransaction);
		
		return transactionList;
	}

	@Override
	protected Charge createCharge(CancelChargeRequest request) {
		Charge charge = chargeRepository.findOne(request.getChargeId());
		return ChargeBuilder.newRefundCharge(charge)
			.setDescription(request.getDescription())
			.setStatementDescription(request.getStatementDescription())
			.setMerchantReferenceId(request.getMerchantReferenceId())
			.build();
	}

	
}
