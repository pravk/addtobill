package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class Transaction {

	public static final String DEBIT = "D";
	public static final String CREDIT = "C";
	
	@Id
	private String transactionId;
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	private String currency;
	private double amount;
	private String userAccountId;
	private String merchantReferenceId;
	private String debitCreditIndicator;
	private String merchantId;

	
	public String getDebitCreditIndicator() {
		return debitCreditIndicator;
	}

	public void setDebitCreditIndicator(String debitCreditIndicator) {
		this.debitCreditIndicator = debitCreditIndicator;
	}
	
	public boolean isDebitTransaction(){
		return DEBIT.equals(getDebitCreditIndicator());
	}
	
	public boolean isCreditTransaction(){
		return CREDIT.equals(getDebitCreditIndicator());
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public String getMerchantReferenceId() {
		return merchantReferenceId;
	}

	public void setMerchantReferenceId(String merchantReferenceId) {
		this.merchantReferenceId = merchantReferenceId;
	}

	public String getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(String userAccountId) {
		this.userAccountId = userAccountId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public double getSignedAmount() {
		return (isDebitTransaction()?-1:1)*getAmount();
	}

}
