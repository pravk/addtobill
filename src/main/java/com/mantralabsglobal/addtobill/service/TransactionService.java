package com.mantralabsglobal.addtobill.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.repository.TransactionRepository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService extends BaseService {

	private TransactionRepository transactionRepository;
	private AccountService accountService;
	private BillingPeriodService billingPeriodService;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Transaction createTransaction(Transaction t) {
		Assert.notNull(t, "Invalid transaction");
		Assert.hasLength(t.getAccountId(), "AccountId cannot be null or empty");

		try {
			accountService.applyTransaction(t);
			return transactionRepository.save(t);
		} catch (Exception e) {
			billingPeriodService.rollbackTransaction(t);
			throw new RuntimeException(e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Transaction cancelTransaction(String transactionId) {
		// Create reverse transaction
		Transaction transaction = transactionRepository.findOne(transactionId);
		Transaction clonedTransaction = clone(transaction, Transaction.class);

		clonedTransaction.setDebitCreditIndicator(
				clonedTransaction.isCreditTransaction() ? Transaction.DEBIT : Transaction.CREDIT);

		return createTransaction(clonedTransaction);

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Transaction updateTransaction(Transaction transaction) {
		// delete transaction
		cancelTransaction(transaction.getTransactionId());
		// create new transaction
		Transaction clonedTransaction = clone(transaction, Transaction.class);
		return createTransaction(clonedTransaction);
	}

	public TransactionRepository getTransactionRepository() {
		return transactionRepository;
	}

	public void setTransactionRepository(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

}
