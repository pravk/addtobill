package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

public class Account{

	public static final String ACCOUNT_STATUS_LOCKED = "Locked";
	public static final String ACCOUNT_STATUS_ACTIVE = "Active";
	public static final String ACCOUNT_STATUS_CLOSED = "Closed";
	
	
	@Id
	private String accountId;
	private double creditLimit;
	
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
}
