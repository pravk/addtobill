package com.mantralabsglobal.addtobill.model;

public class TransactionSuccessResult extends TransactionResult {

	private Transaction userTransaction;
	private Transaction merchantTransaction;

	public TransactionSuccessResult(Transaction userTransaction, Transaction merchantTransaction){
		super("Success");
		this.setUserTransaction(userTransaction);
		this.setMerchantTransaction(merchantTransaction);
	}

	public Transaction getUserTransaction() {
		return userTransaction;
	}

	public void setUserTransaction(Transaction userTransaction) {
		this.userTransaction = userTransaction;
	}

	public Transaction getMerchantTransaction() {
		return merchantTransaction;
	}

	public void setMerchantTransaction(Transaction merchantTransaction) {
		this.merchantTransaction = merchantTransaction;
	}
	
	
}
