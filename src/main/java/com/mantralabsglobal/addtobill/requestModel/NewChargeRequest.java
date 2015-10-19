package com.mantralabsglobal.addtobill.requestModel;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class NewChargeRequest extends ChargeRequest{

	@Id
	private String chargeRequestId;
	private String userId;
	private String token;
	private String merchantId;
	private double amount;
	private String currency;
	private String description;
	private String statementDescription;
	private Date chargeDate;
	
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
	public Date getChargeDate() {
		return chargeDate;
	}
	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

}
