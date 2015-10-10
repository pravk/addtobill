package com.mantralabsglobal.addtobill.model;

import com.mantralabsglobal.addtobill.exception.AccountNotFoundException;
import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.exception.InvalidRequestException;

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

	public TransactionFailureResult(InvalidRequestException e) {
		this("Invalid request");
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
