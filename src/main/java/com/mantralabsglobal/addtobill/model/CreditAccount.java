package com.mantralabsglobal.addtobill.model;

import java.util.Date;

import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;

public class CreditAccount extends Account {

	
	public CreditAccount()
	{
		super();
		setAccountType("C");
	}

	@Override
	public void applyTransaction(Transaction transaction) throws InsufficientBalanceException {
		double newBalance = getAccountBalance().getRunningBalance();
		if(transaction.isCreditTransaction())
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
			getAccountBalance().setBalanceUpdateDate(new Date());
		}
	}

}
