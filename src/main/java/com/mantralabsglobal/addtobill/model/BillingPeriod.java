package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class BillingPeriod {

	public static final String BILLING_PERIOD_STATUS_CLOSED="C";
	public static final String BILLING_PERIOD_STATUS_OPEN="O";
	public static final String BILLING_PERIOD_STATUS_LOCKED="L";
	
	public String getBillingPeriodId() {
		return billingPeriodId;
	}

	public void setBillingPeriodId(String billingPeriodId) {
		this.billingPeriodId = billingPeriodId;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Id
	private String billingPeriodId;
	private long startDate;
	private long endDate;
	private String accountId;
	private String status;
	private double openingBalance;
	
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
		return BILLING_PERIOD_STATUS_OPEN.equals(getStatus());
	}
}
