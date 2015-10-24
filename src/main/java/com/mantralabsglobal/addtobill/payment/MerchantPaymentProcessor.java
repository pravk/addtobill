package com.mantralabsglobal.addtobill.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.MerchantPayment;
import com.mantralabsglobal.addtobill.model.Payment;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.requestModel.MerchantPaymentRequest;

@Component
public class MerchantPaymentProcessor extends PaymentProcessor<MerchantPaymentRequest> {

	@Override
	protected Map<Account, Transaction> createTransactions(Payment payment) {
		Map<Account, Transaction> transactionList = new HashMap<>();
		
		Account account = accountRepository.findOne(payment.getAccountId());
		
		Transaction transaction = new Transaction();
		transaction.setAmount(payment.getPaymentAmount());
		transaction.setCurrency(account.getCurrency());
		transaction.setPaymentId(payment.getPaymentId());
		transaction.setDebitCreditIndicator(Transaction.DEBIT);
		transaction.setTransactionAccountId(account.getAccountId());
		transactionList.put(account, transaction);
		
		return transactionList;
	}

	@Override
	protected Payment createPayment(MerchantPaymentRequest request) {
	
		MerchantPayment payment = new MerchantPayment();
		payment.setAccountId(request.getAccountId());
		payment.setCutOffDate(request.getCutOffDate());
		payment.setPaymentAmount(request.getPaymentAmount());
		payment.setPaymentDate(request.getPaymentDate());
		return payment;
		
	}

}
