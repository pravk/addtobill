package com.mantralabsglobal.addtobill.requestModel;

public class CancelChargeRequest extends ChargeRequest{

	private String chargeId;
	private String description;
	private String statementDescription;
	private long refundDate;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatementDescription() {
		return statementDescription;
	}
	public void setStatementDescription(String statementDescription) {
		this.statementDescription = statementDescription;
	}
	
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public long getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(long refundDate) {
		this.refundDate = refundDate;
	}

}
