package com.mantralabsglobal.addtobill.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;

public class Account{

	public static final String ACCOUNT_STATUS_LOCKED = "Locked";
	public static final String ACCOUNT_STATUS_ACTIVE = "Active";
	public static final String ACCOUNT_STATUS_CREATED = "Created";
	public static final String ACCOUNT_STATUS_CLOSED = "Closed";
	
	
	@Id
	private String accountId;
	private double creditLimit;
	private double unbilledAmount;
	private double remainingCreditBalance;
	
	private List<Transaction> unbilledTransactionList;
	private List<Transaction> billedTransactionList;
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

	public List<Transaction> getUnbilledTransactionList() {
		return unbilledTransactionList;
	}

	public void setUnbilledTransactionList(List<Transaction> unbilledTransactionList) {
		this.unbilledTransactionList = unbilledTransactionList;
	}

	public List<Transaction> getBilledTransactionList() {
		return billedTransactionList;
	}

	public void setBilledTransactionList(List<Transaction> billedTransactionList) {
		this.billedTransactionList = billedTransactionList;
	}

	public double getRemainingCreditBalance() {
		return remainingCreditBalance;
	}

	public void setRemainingCreditBalance(double remainingCreditBalance) {
		this.remainingCreditBalance = remainingCreditBalance;
	}

	public synchronized boolean addToUnbilledTransactionList(Transaction t) throws InsufficientBalanceException{
		if(hasSufficientBalance(t)){
			if(unbilledTransactionList == null)
				unbilledTransactionList = new ArrayList<>();
			unbilledTransactionList.add(t);
			remainingCreditBalance = remainingCreditBalance + t.getSignedAmount();
			return true;
		}
		else
		{
			throw new InsufficientBalanceException();
		}
	}
	

	protected boolean hasSufficientBalance(Transaction t) {
		return getRemainingCreditBalance() + t.getSignedAmount() <= getCreditLimit();
	}
}
