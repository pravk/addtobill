package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class UserMerchantIdentity extends BaseEntity{

	@Id
	private String userMerchantIdentityId;
	private String userId;
	private String merchantId;
	private String merchantUserId;
	
	public String getUserId() {
		return userId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	
	
	
}
