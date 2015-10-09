package com.mantralabsglobal.addtobill.service;

import org.springframework.stereotype.Service;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.BillingPeriod;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.AccountRepository;
import com.mantralabsglobal.addtobill.repository.BillingPeriodRepository;

@Service
public class AccountService  extends BaseService{

	private AccountRepository accountRepository;
	private BillingPeriodRepository periodRepository;
	private BillingPeriodService billingPeriodService;

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
		account.setStatus(Account.ACCOUNT_STATUS_ACTIVE);
		return accountRepository.save(account);
	}
	
	

	public boolean applyTransaction(Transaction t) {
		Account account = accountRepository.findOne(t.getAccountId());
		BillingPeriod period = billingPeriodService.getCurrentBillingPeriod(t.getAccountId());
		if( !period.isOpen())
			throw new RuntimeException("Billing period is not open");
		
		if(billingPeriodService.canApplyTransaction(t, account, period)){
			period.setRunningBalance(period.getRunningBalance() + t.getSignedAmount());
			periodRepository.save(period);
			return true;
		}
		else
		{
			throw new RuntimeException("Insufficient funds. Unable to perform transaction");
		}
	}

	
}
