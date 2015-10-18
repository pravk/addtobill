package com.mantralabsglobal.addtobill.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.mantralabsglobal.addtobill.Application;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.BillingPeriod;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.AccountRepository;
import com.mantralabsglobal.addtobill.repository.BillingPeriodRepository;
import com.mantralabsglobal.addtobill.repository.TransactionRepository;
import com.mantralabsglobal.addtobill.repository.UserRepository;

@Component
public class BillingPeriodService {
	
	@PostConstruct
	public void inti(){
		getApplication().registerForTransactionBus(this);
	}

	@Autowired
	private AccountRepository accountrepository;
	
	@Autowired
	private Application application;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BillingPeriodRepository billingPeriodRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Subscribe
	@AllowConcurrentEvents
	public void handleTransaction(Transaction transaction){
		
		Account account = accountrepository.findOne(transaction.getTransactionAccountId());
		User user = userRepository.findOne(account.getOwnerId());
		if(user != null)
		{
			Date billPeriodStart = DateUtils.truncate(new Date(), Calendar.DATE);
			Date billPeriodEnd = DateUtils.ceiling(new Date(), Calendar.DATE);
			
			
			BillingPeriod billingPeriod =billingPeriodRepository.findOneByDateAndAccount( billPeriodStart , account.getAccountId());
			if(billingPeriod == null)
			{
				BillingPeriod lastPeriod = billingPeriodRepository.findOneClosedByAccountId(account.getAccountId(), new Sort(Direction.DESC, "endDate"));
				
				billingPeriod = new BillingPeriod();
				billingPeriod.setAccountId(account.getAccountId());
				billingPeriod.setStartDate(Calendar.getInstance().getTime());
				billingPeriod.setEndDate(billPeriodEnd);
				billingPeriod.setOpeningBalance(lastPeriod== null?0:lastPeriod.getClosingBalance());
				billingPeriod = billingPeriodRepository.save(billingPeriod);
			}
			transaction.putAttribute("billingPeriod", billingPeriod.getBillingPeriodId());
			transactionRepository.save(transaction);
			
		}
	}

	public AccountRepository getAccountrepository() {
		return accountrepository;
	}

	public void setAccountrepository(AccountRepository accountrepository) {
		this.accountrepository = accountrepository;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public BillingPeriodRepository getBillingPeriodRepository() {
		return billingPeriodRepository;
	}

	public void setBillingPeriodRepository(BillingPeriodRepository billingPeriodRepository) {
		this.billingPeriodRepository = billingPeriodRepository;
	}

	public TransactionRepository getTransactionRepository() {
		return transactionRepository;
	}

	public void setTransactionRepository(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
}
