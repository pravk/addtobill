package com.mantralabsglobal.addtobill.billing;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class BillGenerator {

	Logger logger = LoggerFactory.getLogger(BillGenerator.class);
	@Autowired
	Application application;
	
	@Autowired
	BillingPeriodRepository billingPeriodRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@PostConstruct
	public void init(){
		application.registerForBillingBus(this);
	}
	
	@Subscribe
	public void generateBill(BillingPeriod period){
		if("L".equals(period.getStatus())){
			try
			{
				Account account = accountRepository.findOne(period.getAccountId());
				User user = userRepository.findOne(account.getOwnerId());
				//Compute closing balance
				Iterator<Transaction> transactionIterator = transactionRepository.findByBillingPeriod(period.getBillingPeriodId()).iterator();
				double closingBalance = period.getOpeningBalance();
				while(transactionIterator.hasNext()){
					Transaction transaction = transactionIterator.next();
					closingBalance += (transaction.isDebitTransaction()?1:-1)*transaction.getAmount();
				}
				period.setClosingBalance(closingBalance);
				period = billingPeriodRepository.save(period);
				logger.info("Total amount due {}", period.getClosingBalance());
				
				//Generate PDF
				logger.info("Generate PDF");
				
				//Email PDF
				logger.info("Attach and send pdf to {}" , user.getEmail());
				
				//Mark period as closed
				period.setStatus("C");
				period = billingPeriodRepository.save(period);
				application.postBillingPeriodStatusChange(period);
			}
			catch(Exception exp){
				period.setStatus("E");
				period.setFailureMessage(exp.getMessage());
				billingPeriodRepository.save(period);
				throw exp;
			}
			
		}
	}
}
