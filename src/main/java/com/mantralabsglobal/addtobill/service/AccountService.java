package com.mantralabsglobal.addtobill.service;

import org.springframework.stereotype.Service;
import com.mantralabsglobal.addtobill.model.UserAccount;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.MerchantRepository;

@Service
public class AccountService  extends BaseService{
	
	public UserAccount createAccount(User user){
		UserAccount account = new UserAccount();
		account.setCreatedBy(user.getUserId());
		account.setCreditLimit(5000);
		account.setRemainingCreditBalance(account.getCreditLimit());
		account.setStatus(UserAccount.ACCOUNT_STATUS_ACTIVE);
		account.setUserId(user.getUserId());
		return accountRepository.save(account);
	}
/*
	public Transaction createTransaction(Transaction t) throws InsufficientBalanceException, InvalidRequestException {
		
		validator.validateNewTransaction(t);
		
		Account account = accountRepository.findOne(t.getUserAccountId());
		Merchant merchant = getMerchantRepository().findOne(t.getMerchantId());
							
		if(account != null && merchant != null){
			account.applyTransaction(t);
			t.setTransactionId(ObjectId.get().toString());
			accountRepository.save(account);
			return t;
		}
		else 
		{
			throw new InvalidRequestException();
		}
		
	}*/


	public Transaction cancelTransaction(String transactionId) {
		return null;
	}

	public Transaction updateTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

	public Transaction getTransaction(String transactionId) {
		return accountRepository.findOneByTransactionId(transactionId);
	}

	public MerchantRepository getMerchantRepository() {
		return merchantRepository;
	}

	public void setMerchantRepository(MerchantRepository merchantRepository) {
		this.merchantRepository = merchantRepository;
	}


}
