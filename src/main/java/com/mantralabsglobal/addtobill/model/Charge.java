package com.mantralabsglobal.addtobill.model;

import java.util.Map;

import org.springframework.data.annotation.Id;

public class Charge extends BaseEntity {

	public static final String CHARGE_TYPE_CHARGE = "charge";
	public static final String CHARGE_TYPE_REFUND = "refund";
	
	public static final String CHARGE_STATUS_RECORDED = "recorded";
	public static final String CHARGE_STATUS_FAILED = "failed";
	public static final String CHARGE_STATUS_SUCCESS = "success";
	
	@Id
	private String chargeId;
	private long chargeDate;
	private double amount;
	private double applicationFee;
	private String currency;
	private String userId;
	private String description;
	private String merchantId;
	private String failureCode;
	private String failureMessage;
	private Map<String,String> metadata;
	private boolean paid;
	private String reciptEmail;
	private String reciptNumber;
	private Shipping shippingInfo;
	private String statementDescriptor;
	private String status;
	private String chargeType;
	private String linkedChargeId;
	
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getApplicationFee() {
		return applicationFee;
	}
	public void setApplicationFee(double applicationFee) {
		this.applicationFee = applicationFee;
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFailureCode() {
		return failureCode;
	}
	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	public Map<String, String> getMetadata() {
		return metadata;
	}
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public String getReciptEmail() {
		return reciptEmail;
	}
	public void setReciptEmail(String reciptEmail) {
		this.reciptEmail = reciptEmail;
	}
	public String getReciptNumber() {
		return reciptNumber;
	}
	public void setReciptNumber(String reciptNumber) {
		this.reciptNumber = reciptNumber;
	}
	public Shipping getShippingInfo() {
		return shippingInfo;
	}
	public void setShippingInfo(Shipping shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
	public String getStatementDescriptor() {
		return statementDescriptor;
	}
	public void setStatementDescriptor(String statementDescriptor) {
		this.statementDescriptor = statementDescriptor;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getLinkedChargeId() {
		return linkedChargeId;
	}
	public void setLinkedChargeId(String linkedChargeId) {
		this.linkedChargeId = linkedChargeId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantId() {
		return this.merchantId ;
	}
	public long getChargeDate() {
		return chargeDate;
	}
	public void setChargeDate(long chargeDate) {
		this.chargeDate = chargeDate;
	}
	
}
