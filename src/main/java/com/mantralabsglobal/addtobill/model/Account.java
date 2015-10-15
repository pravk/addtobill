package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;

public abstract class Account extends BaseEntity {

	@Id
	private String accountId;
	private String currency;
	private String ownerId;	
	private String accountType;

	private double upperLimit;
	private double lowerLimit;
	
	private AccountBalance accountBalance;
	
	public String getAccountType(){
		return accountType;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public AccountBalance getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(AccountBalance accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public boolean isCreditAccount() {
		return "C".equals(getAccountType());
	}
	
	public boolean isDebitAccount() {
		return "D".equals(getAccountType());
	}

	public abstract void applyTransaction(Transaction transaction) throws InsufficientBalanceException;

	public double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


}
