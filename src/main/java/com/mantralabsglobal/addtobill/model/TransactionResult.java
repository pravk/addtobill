package com.mantralabsglobal.addtobill.model;

public abstract class TransactionResult {

	private String status;
	
	public TransactionResult(String status2) {
		this.status = status2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
