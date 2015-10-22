package com.mantralabsglobal.addtobill.communication;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.mantralabsglobal.addtobill.Application;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.BillingPeriod;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.AccountRepository;
import com.mantralabsglobal.addtobill.repository.UserRepository;

@Component
public class EmailSender {

	private static Logger logger = LoggerFactory.getLogger(EmailSender.class);
	
	private static int MAX_RETRY_COUNT=3;
	
	@Autowired
	private JavaMailSender sender;
	
	@Value("${app.mail.from}")
    private String from;
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Application application;
	
	@PostConstruct
	public void init(){
		application.registerForTransactionBus(this);
		application.registerForBillingBus(this);
	}
	
	@Subscribe
	public void sendTransactionAlert(Transaction transaction){
		String accountId = transaction.getTransactionAccountId();
		
		Account account = accountRepository.findOne(accountId);
		User user = userRepository.findOne(account.getOwnerId());
		
		if(user != null)
		{
			SimpleMailMessage mailMessage = new SimpleMailMessage();
	        mailMessage.setTo(user.getEmail());
	        mailMessage.setFrom(from);
	        mailMessage.setSubject("New Transaction");
	        mailMessage.setText(String.format("Your account %s has been %s by %s %s", account.getAccountId(), transaction.isCreditTransaction()?"credited":"debited" ,account.getCurrency(), transaction.getAmount() ));
	        sendMessage(mailMessage, 0);
	     
		} 
	}
	
	@Subscribe
	public void sendBillCycleAlert(BillingPeriod billingPeriod){
		if(billingPeriod.isClosed())
		{
			String accountId = billingPeriod.getAccountId();
			
			Account account = accountRepository.findOne(accountId);
			User user = userRepository.findOne(account.getOwnerId());
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
	        mailMessage.setTo(user.getEmail());
	        mailMessage.setFrom(from);
	        mailMessage.setSubject("New bill generated!");
	        mailMessage.setText(String.format("Total amount due is %s %s", account.getCurrency(), billingPeriod.getClosingBalance() ));
	        sendMessage(mailMessage, 0);
		}
		 
	}
	
	protected void sendMessage(SimpleMailMessage message, int retryCounter){
		try
		{
			sender.send(message);
		}
		catch(Exception exp){
			logger.warn("Failed to send message ", exp);
			if(retryCounter< MAX_RETRY_COUNT)
				sendMessage(message, retryCounter++);
		}
	}
}
