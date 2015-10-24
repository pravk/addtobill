package com.mantralabsglobal.addtobill.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Payment extends BaseEntity{
	
	public static final String PAYMENT_STATUS_RECORDED = "RECORDED";

	public static final String PAYMENT_STATUS_SUCCESS = "SUCCESS";

	public static final String PAYMENT_STATUS_FAILED = "FAILED";

	@Id
	private String paymentId;
	private String status;
	
	private double paymentAmount;
	private Date paymentDate;
	private String accountId;
	private String failureMessage;
	
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	
}
