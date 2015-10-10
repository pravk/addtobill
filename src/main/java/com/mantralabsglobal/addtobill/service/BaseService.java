package com.mantralabsglobal.addtobill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.UserAccount;
import com.mantralabsglobal.addtobill.repository.AccountRepository;
import com.mantralabsglobal.addtobill.repository.MerchantRepository;
import com.mantralabsglobal.addtobill.repository.UserAccountRepository;
import com.mantralabsglobal.addtobill.repository.UserRepository;

public abstract class BaseService {
	
	@Autowired
	protected AccountRepository accountRepository;
	@Autowired
	protected UserRepository userRepository;
	@Autowired
	protected MerchantRepository merchantRepository;
	@Autowired
	protected UserAccountRepository userAccountRepository;
	
	protected <T> T clone(T object, Class<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(object), type);
	}

	
	public Account getAccountDetails(String accountId){
		return accountRepository.findOne(accountId);
	}


	public MerchantRepository getMerchantRepository() {
		return merchantRepository;
	}


	public void setMerchantRepository(MerchantRepository merchantRepository) {
		this.merchantRepository = merchantRepository;
	}


	public UserAccountRepository getUserAccountRepository() {
		return userAccountRepository;
	}


	public void setUserAccountRepository(UserAccountRepository userAccountRepository) {
		this.userAccountRepository = userAccountRepository;
	}
	

	protected String getUserIdByEmail(String email) {
		return userRepository.findOneByEmail(email).getUserId();
	}
	
	protected Transaction getUserTransaction(String userId, String transactionId) {
		List<UserAccount> userAccountList = getUserAccountRepository().findAllByUserId(userId);
		for(UserAccount userAccount: userAccountList){
			Transaction transaction = accountRepository.findOneByAccountIdAndTransactionId(userAccount.getAccountId(), transactionId);
			if(transaction != null)
				return transaction;
		}
		return null;
	}
	
}
