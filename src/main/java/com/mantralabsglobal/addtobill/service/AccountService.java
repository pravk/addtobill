package com.mantralabsglobal.addtobill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mantralabsglobal.addtobill.exception.AccountNotFoundException;
import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.AccountRepository;

@Service
public class AccountService  extends BaseService{

	@Autowired
	private AccountRepository accountRepository;
	
	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	public Account getAccountDetails(String accountId){
		return accountRepository.findOne(accountId);
	}
	
	public Account lockAccount(String accountId, String reason){
		Account account = accountRepository.findOne(accountId);
		account.setStatus(Account.ACCOUNT_STATUS_LOCKED);
		return accountRepository.save(account);
	}
	
	public Account closeAccount(String accountId, String reason){
		Account account = accountRepository.findOne(accountId);
		account.setStatus(Account.ACCOUNT_STATUS_CLOSED);
		return accountRepository.save(account);
	}
	
	public Account createAccount(User user){
		Account account = new Account();
		account.setCreatedBy(user.getUserId());
		account.setCreditLimit(5000);
		account.setRemainingCreditBalance(account.getCreditLimit());
		account.setStatus(Account.ACCOUNT_STATUS_ACTIVE);
		return accountRepository.save(account);
	}

	public Transaction createTransaction(Transaction t) throws InsufficientBalanceException, AccountNotFoundException {
		Account account = accountRepository.findOne(t.getAccountId());
		
		if(account != null){
			account.addToUnbilledTransactionList(t);
			accountRepository.save(account);
			return t;
		}
		else
		{
			throw new AccountNotFoundException();
		}
	}


	public Transaction cancelTransaction(String transactionId) {
		return null;
	}

	public Transaction updateTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

}
