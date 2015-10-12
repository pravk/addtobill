package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class BankAccount {

	@Id
	private String bankAccountId;
	private String account;
	private String bank_name;
	private String country;
	private String currency;
	private String last4;
	private boolean defaultForCurrency;
	
	public String getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getLast4() {
		return last4;
	}
	public void setLast4(String last4) {
		this.last4 = last4;
	}
	public boolean isDefaultForCurrency() {
		return defaultForCurrency;
	}
	public void setDefaultForCurrency(boolean defaultForCurrency) {
		this.defaultForCurrency = defaultForCurrency;
	}
	
}
