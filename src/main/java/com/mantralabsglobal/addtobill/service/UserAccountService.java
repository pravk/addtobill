package com.mantralabsglobal.addtobill.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.AccountBalance;
import com.mantralabsglobal.addtobill.model.DebitAccount;
import com.mantralabsglobal.addtobill.model.User;

@Service
public class UserAccountService  extends BaseService{
	
	public Account createAccount(User user, String currency){
		Assert.hasText(currency);
		
		DebitAccount account = new DebitAccount();
		account.setCreatedBy(user.getUserId());
		account.setUpperLimit(5000);
		account.setLowerLimit(Double.MIN_VALUE);
		AccountBalance acctBalance = new AccountBalance();
		acctBalance.setRunningBalance(0);
		acctBalance.setBalanceUpdateDate(new Date().getTime());
		account.setAccountBalance(acctBalance);
		account.setOwnerId(user.getUserId());
		account.setCurrency(currency);
		return accountRepository.save(account);
	}

	public boolean accountExists(User savedUser, String currency) {
		return accountRepository.findOneByOwnerIdAndCurrency(savedUser.getUserId(), currency) != null;
	}

}
