package com.mantralabsglobal.addtobill.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.MerchantRepository;
import com.mantralabsglobal.addtobill.repository.UserAccountRepository;
import com.mantralabsglobal.addtobill.validators.TransactionValidator;

@Service
public class AccountService  extends BaseService{
	
	@Autowired
	private TransactionValidator validator;
	
	
	public Account createAccount(User user){
		Account account = new Account();
		account.setCreatedBy(user.getUserId());
		account.setCreditLimit(5000);
		account.setRemainingCreditBalance(account.getCreditLimit());
		account.setStatus(Account.ACCOUNT_STATUS_ACTIVE);
		return accountRepository.save(account);
	}

	public Transaction createTransaction(Transaction t) throws InsufficientBalanceException, InvalidRequestException {
		
		validator.validateNewTransaction(t);
		
		Account account = accountRepository.findOne(t.getAccountId());
		Merchant merchant = getMerchantRepository().findOne(t.getMerchantId());
		
		if(account != null && merchant != null){
			account.addToUnbilledTransactionList(t);
			t.setTransactionId(ObjectId.get().toString());
			accountRepository.save(account);
			return t;
		}
		else 
		{
			throw new InvalidRequestException();
		}
		
	}


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

	public TransactionValidator getValidator() {
		return validator;
	}

	public void setValidator(TransactionValidator validator) {
		this.validator = validator;
	}

	

	public UserAccountRepository getUserAccountRepository() {
		return userAccountRepository;
	}

	public void setUserAccountRepository(UserAccountRepository userAccountRepository) {
		this.userAccountRepository = userAccountRepository;
	}

}
