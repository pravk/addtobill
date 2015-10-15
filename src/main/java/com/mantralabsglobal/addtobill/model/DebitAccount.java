package com.mantralabsglobal.addtobill.model;

import java.util.Date;

import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;

public class DebitAccount extends Account{

	public DebitAccount()
	{
		super();
		setAccountType("D");
	}
	

	@Override
	public void applyTransaction(Transaction transaction) throws InsufficientBalanceException {
		double newBalance = getAccountBalance().getRunningBalance();
		if(transaction.isDebitTransaction())
		{
			newBalance += transaction.getAmount();
		}
		else
		{
			newBalance -= transaction.getAmount();
		}
		if(newBalance > getUpperLimit()|| newBalance <getLowerLimit()){
			throw new InsufficientBalanceException();
		}
		else
		{
			getAccountBalance().setRunningBalance(newBalance);
			getAccountBalance().setBalanceUpdateDate(new Date().getTime());
		}
	}
}
