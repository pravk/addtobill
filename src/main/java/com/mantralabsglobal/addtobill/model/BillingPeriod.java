package com.mantralabsglobal.addtobill.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class BillingPeriod extends BaseEntity {

	@Id
	private String billingPeriodId;
	private Date startDate;
	private Date endDate;
	private String accountId;
	private double openingBalance;
	private String status;
	private String failureMessage;
	
	public String getBillingPeriodId() {
		return billingPeriodId;
	}

	public void setBillingPeriodId(String billingPeriodId) {
		this.billingPeriodId = billingPeriodId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public double getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(double closingBalance) {
		this.closingBalance = closingBalance;
	}

	private double closingBalance;
	
	public boolean isOpen() {
		return "O".equals(getStatus());
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setFailureMessage(String message) {
		this.failureMessage = message;
	}
	
	public String getFailureMessage(){
		return this.failureMessage;
	}
}
