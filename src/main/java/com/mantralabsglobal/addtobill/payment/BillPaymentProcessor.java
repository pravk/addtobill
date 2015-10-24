package com.mantralabsglobal.addtobill.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.BillPayment;
import com.mantralabsglobal.addtobill.model.Payment;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.repository.BillingPeriodRepository;
import com.mantralabsglobal.addtobill.requestModel.BillPaymentRequest;

@Component
public class BillPaymentProcessor extends PaymentProcessor<BillPaymentRequest> {

	@Autowired
	protected BillingPeriodRepository billingPeriodRepository;
	
	@Override
	protected Map<Account, Transaction> createTransactions(Payment payment) {
		Map<Account, Transaction> transactionList = new HashMap<>();
		
		Account account = accountRepository.findOne(payment.getAccountId());
		
		Transaction userTransaction = new Transaction();
		userTransaction.setAmount(payment.getPaymentAmount());
		userTransaction.setCurrency(account.getCurrency());
		userTransaction.setPaymentId(payment.getPaymentId());
		userTransaction.setDebitCreditIndicator(Transaction.CREDIT);
		userTransaction.setTransactionAccountId(account.getAccountId());
		transactionList.put(account, userTransaction);
		
		return transactionList;
	}

	@Override
	protected Payment createPayment(BillPaymentRequest request) {
		BillPayment billPayment = new BillPayment();
		
		Account account = accountRepository.findOne(request.getAccountId());
		
		billPayment.setAccountId(account.getAccountId());
		billPayment.setBillingPeriodId(request.getBillingPeriodId());
		billPayment.setPaymentAmount(request.getPaymentAmount());
		billPayment.setPaymentDate(request.getPaymentDate());
		
		return billPayment;
		
	}

}
