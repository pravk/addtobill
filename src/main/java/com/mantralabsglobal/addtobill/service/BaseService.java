package com.mantralabsglobal.addtobill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.repository.AccountRepository;
import com.mantralabsglobal.addtobill.repository.MerchantAccountRepository;
import com.mantralabsglobal.addtobill.repository.MerchantRepository;
import com.mantralabsglobal.addtobill.repository.UserRepository;

public abstract class BaseService {
	
	@Autowired
	protected AccountRepository accountRepository;
	@Autowired
	protected MerchantAccountRepository merchantAccountRepository;
	@Autowired
	protected UserRepository userRepository;
	@Autowired
	protected MerchantRepository merchantRepository;
	
	
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

	protected String getUserIdByEmail(String email) {
		return userRepository.findOneByEmail(email).getUserId();
	}
	
	protected Transaction getUserTransaction(String userId, String transactionId) {
		List<Account> userAccountList = accountRepository.findAllByUserId(userId);
		for(Account userAccount: userAccountList){
			Transaction transaction = accountRepository.findOneByAccountIdAndTransactionId(userAccount.getAccountId(), transactionId);
			if(transaction != null)
				return transaction;
		}
		return null;
	}
	
}
