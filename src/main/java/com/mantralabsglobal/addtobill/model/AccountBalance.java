package com.mantralabsglobal.addtobill.model;

import java.util.Date;

public class AccountBalance {

	private double runningBalance;
	private Date balanceUpdateDate;
	
	public double getRunningBalance() {
		return runningBalance;
	}
	public void setRunningBalance(double runningBalance) {
		this.runningBalance = runningBalance;
	}
	public Date getBalanceUpdateDate() {
		return balanceUpdateDate;
	}
	public void setBalanceUpdateDate(Date balanceUpdateDate) {
		this.balanceUpdateDate = balanceUpdateDate;
	}
	
	
}
