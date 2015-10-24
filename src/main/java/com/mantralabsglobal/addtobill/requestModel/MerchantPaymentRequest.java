package com.mantralabsglobal.addtobill.requestModel;

import java.util.Date;

public class MerchantPaymentRequest extends PaymentRequest{

	private String accountId;
	
	private Date cutOffDate;

	public Date getCutOffDate() {
		return cutOffDate;
	}

	public void setCutOffDate(Date cutOffDate) {
		this.cutOffDate = cutOffDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccount(String accountId) {
		this.accountId = accountId;
	}
	
	
}
