package com.mantralabsglobal.addtobill.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mantralabsglobal.addtobill.model.UserAccount;
import com.mantralabsglobal.addtobill.model.User;

@Service
public class UserAccountService  extends BaseService{
	
	public UserAccount createAccount(User user, String currency){
		Assert.hasText(currency);
		
		UserAccount account = new UserAccount();
		account.setCreatedBy(user.getUserId());
		account.setCreditLimit(5000);
		account.setRemainingCreditBalance(account.getCreditLimit());
		account.setStatus(UserAccount.ACCOUNT_STATUS_ACTIVE);
		account.setUserId(user.getUserId());
		account.setCurrency(currency);
		return accountRepository.save(account);
	}

	public boolean accountExists(User savedUser, String currency) {
		return accountRepository.findOneByUserIdAndCurrency(savedUser.getUserId(), currency) != null;
	}

}
