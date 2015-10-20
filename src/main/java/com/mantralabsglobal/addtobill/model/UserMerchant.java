package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class UserMerchant {

	@Id
	private String userMerchantId;
	private String userId;
	private String merchantId;
	private String vendorUserId;
	
	public String getUserMerchantId() {
		return userMerchantId;
	}
	public void setUserMerchantId(String userMerchantId) {
		this.userMerchantId = userMerchantId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getVendorUserId() {
		return vendorUserId;
	}
	public void setVendorUserId(String vendorUserId) {
		this.vendorUserId = vendorUserId;
	}
	
}
