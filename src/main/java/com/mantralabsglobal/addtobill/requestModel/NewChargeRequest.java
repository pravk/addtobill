package com.mantralabsglobal.addtobill.requestModel;

public class NewChargeRequest extends ChargeRequest{

	private String userId;
	private String token;
	private String merchantId;
	private double amount;
	private String currency;
	private String description;
	private String statementDescription;
	private long chargeDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatementDescription() {
		return statementDescription;
	}
	public void setStatementDescription(String statementDescription) {
		this.statementDescription = statementDescription;
	}
	public long getChargeDate() {
		return chargeDate;
	}
	public void setChargeDate(long chargeDate) {
		this.chargeDate = chargeDate;
	}

}
