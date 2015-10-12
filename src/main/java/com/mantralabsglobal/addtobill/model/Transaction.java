package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class Transaction {

	public static final String DEBIT = "D";
	public static final String CREDIT = "C";
	
	@Id
	private String transactionId;

	private String currency;
	private double amount;
	private String transactionAccountId;
	private String debitCreditIndicator;
	private String chargeId;
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
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

	public double getSignedAmount() {
		return (isDebitTransaction()?-1:1)*getAmount();
	}

	public String getTransactionAccountId() {
		return transactionAccountId;
	}

	public void setTransactionAccountId(String transactionAccountId) {
		this.transactionAccountId = transactionAccountId;
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

}
