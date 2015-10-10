package com.mantralabsglobal.addtobill.model;

public class TransactionSuccessResult extends TransactionResult {

	private String transactionId;
	private String vendorRefrenceId;

	public TransactionSuccessResult(String transactionId, String vendorRefrenceId){
		super("Success");
		this.setTransactionId(transactionId);
		this.setVendorRefrenceId(vendorRefrenceId);
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getVendorRefrenceId() {
		return vendorRefrenceId;
	}

	public void setVendorRefrenceId(String vendorRefrenceId) {
		this.vendorRefrenceId = vendorRefrenceId;
	}
}
