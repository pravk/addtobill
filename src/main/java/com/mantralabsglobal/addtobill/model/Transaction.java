package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class Transaction {

	public static final String DEBIT = "D";
	public static final String CREDIT = "C";
	
	@Id
	private String transactionId;
	private String currency;
	private double amount;
	private String billingPeriodId;
	private String accountId;
	private String merchantId;
	private String vendorReferenceId;
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	private String debitCreditIndicator;
	

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

	public String getBillingPeriodId() {
		return billingPeriodId;
	}

	public void setBillingPeriodId(String billingPeriodId) {
		this.billingPeriodId = billingPeriodId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public double getSignedAmount() {
		return (isDebitTransaction()?1:-1)*getAmount();
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getVendorReferenceId() {
		return vendorReferenceId;
	}

	public void setVendorReferenceId(String vendorReferenceId) {
		this.vendorReferenceId = vendorReferenceId;
	}

}
