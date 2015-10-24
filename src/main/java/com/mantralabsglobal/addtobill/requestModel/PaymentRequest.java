package com.mantralabsglobal.addtobill.requestModel;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class PaymentRequest extends Request{

	@Id
	private String paymentRequestId;
	private String vendorReferenceId;
	
	private String accountId;
	
	private double paymentAmount;
	private Date paymentDate;
	
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
	public String getPaymentRequestId() {
		return paymentRequestId;
	}
	public void setPaymentRequestId(String paymentRequestId) {
		this.paymentRequestId = paymentRequestId;
	}
	public String getVendorReferenceId() {
		return vendorReferenceId;
	}
	public void setVendorReferenceId(String vendorReferenceId) {
		this.vendorReferenceId = vendorReferenceId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}
