package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class MerchantAccount extends BaseEntity {
	
	@Id
	private String merchantAccountId;
	private String merchantId;
	private AccountBalance accountBalance;
	private String currency;
	
	public String getMerchantAccountId() {
		return merchantAccountId;
	}
	public void setMerchantAccountId(String merchantAccountId) {
		this.merchantAccountId = merchantAccountId;
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public AccountBalance getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(AccountBalance accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
