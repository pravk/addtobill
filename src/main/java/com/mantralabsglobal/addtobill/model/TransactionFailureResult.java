package com.mantralabsglobal.addtobill.model;

import com.mantralabsglobal.addtobill.exception.AccountNotFoundException;
import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;

public class TransactionFailureResult extends TransactionResult {

	private String reason;
	
	public TransactionFailureResult(String reason){
		super("Failure");
		this.setReason(reason);
	}
	
	public TransactionFailureResult(InsufficientBalanceException e) {
		this("Insifficient Balance");
	}

	public TransactionFailureResult(AccountNotFoundException e) {
		this("Account not found");
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
