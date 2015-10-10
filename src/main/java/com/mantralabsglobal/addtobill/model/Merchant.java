package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class Merchant {

	@Id
	private String merchantId;
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	private String merchantName;
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
}
