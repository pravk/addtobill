package com.mantralabsglobal.addtobill.requestModel;

public class BillPaymentRequest extends PaymentRequest{
	
	private String billingPeriodId;
	public String getBillingPeriodId() {
		return billingPeriodId;
	}
	public void setBillingPeriodId(String billingPeriodId) {
		this.billingPeriodId = billingPeriodId;
	}

	
}
