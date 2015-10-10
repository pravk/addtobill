package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class MerchantAccount extends BaseEntity {
	
	@Id
	private String merchantAccountId;
	
	public String getMerchantAccountId() {
		return merchantAccountId;
	}
	public void setMerchantAccountId(String merchantAccountId) {
		this.merchantAccountId = merchantAccountId;
	}
	private String merchantId;
	
	private double balance;
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
}
