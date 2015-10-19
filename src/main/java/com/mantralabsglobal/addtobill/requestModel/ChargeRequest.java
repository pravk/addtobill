package com.mantralabsglobal.addtobill.requestModel;

public abstract class ChargeRequest extends Request{

	private String chargeRequestId;
	private String merchantReferenceId;
	

	public String getChargeRequestId() {
		return chargeRequestId;
	}

	public void setChargeRequestId(String chargeRequestId) {
		this.chargeRequestId = chargeRequestId;
	}

	public String getMerchantReferenceId() {
		return merchantReferenceId;
	}

	public void setMerchantReferenceId(String merchantReferenceId) {
		this.merchantReferenceId = merchantReferenceId;
	}
	
}
