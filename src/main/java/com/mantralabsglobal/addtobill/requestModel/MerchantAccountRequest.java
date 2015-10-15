package com.mantralabsglobal.addtobill.requestModel;

public class MerchantAccountRequest {

	private String merchantId;
	private String currency;
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
}
