package com.mantralabsglobal.addtobill.business;

import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.TransactionResult;

public interface TransactionProcessor {

	TransactionResult createTransaction(Transaction t);

	TransactionResult deleteTransaction(String transactionId);

	TransactionResult updateTransaction(Transaction transaction);
}
