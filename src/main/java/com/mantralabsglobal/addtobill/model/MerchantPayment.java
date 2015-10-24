package com.mantralabsglobal.addtobill.model;

import java.util.Date;

public class MerchantPayment extends Payment {
	
	private Date cutOffDate;

	public Date getCutOffDate() {
		return cutOffDate;
	}

	public void setCutOffDate(Date cutOffDate) {
		this.cutOffDate = cutOffDate;
	}
	
	
	
}
