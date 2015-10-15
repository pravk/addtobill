package com.mantralabsglobal.addtobill.requestModel;

public abstract class ChargeRequest extends Request{

	private String chargeRequestId;

	public String getChargeRequestId() {
		return chargeRequestId;
	}

	public void setChargeRequestId(String chargeRequestId) {
		this.chargeRequestId = chargeRequestId;
	}
	
}
