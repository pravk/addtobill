package com.mantralabsglobal.addtobill.model;

import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

public class UserAccount{

	public static final String ACCOUNT_STATUS_LOCKED = "Locked";
	public static final String ACCOUNT_STATUS_ACTIVE = "Active";
	public static final String ACCOUNT_STATUS_CREATED = "Created";
	public static final String ACCOUNT_STATUS_CLOSED = "Closed";
	
	
	@Id
	private String accountId;
	private String userId;
	private double creditLimit;
	private double unbilledAmount;
	private double remainingCreditBalance;
	
	private List<BillingPeriod> billingPeriodList;
	
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreationTS() {
		return creationTS;
	}

	public void setCreationTS(String creationTS) {
		this.creationTS = creationTS;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdateTS() {
		return updateTS;
	}

	public void setUpdateTS(String updateTS) {
		this.updateTS = updateTS;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@CreatedDate
	private String creationTS;
	@CreatedBy
	private String createdBy;
	@LastModifiedDate
	private String updateTS;
	@LastModifiedBy
	private String updatedBy;
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}


	public List<BillingPeriod> getBillingPeriodList() {
		return billingPeriodList;
	}

	public void setBillingPeriodList(List<BillingPeriod> billingPeriodList) {
		this.billingPeriodList = billingPeriodList;
	}

	public double getUnbilledAmount() {
		return unbilledAmount;
	}

	public void setUnbilledAmount(double unbilledAmount) {
		this.unbilledAmount = unbilledAmount;
	}

	public double getRemainingCreditBalance() {
		return remainingCreditBalance;
	}

	public void setRemainingCreditBalance(double remainingCreditBalance) {
		this.remainingCreditBalance = remainingCreditBalance;
	}

	public boolean hasSufficientBalance(Transaction t) {
		return getRemainingCreditBalance() + t.getSignedAmount()>=0;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
