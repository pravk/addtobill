package com.mantralabsglobal.addtobill.service;

import java.security.Principal;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.Subscribe;
import com.mantralabsglobal.addtobill.Application;
import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.repository.AccountRepository;
import com.mantralabsglobal.addtobill.repository.MerchantRepository;

@Service
public class MerchantService extends BaseService{

	private static final Logger logger = LoggerFactory.getLogger(MerchantService.class);
	
	@Autowired
	private Application application;
	
	@Autowired
	private AccountRepository accountrepository;
	
	
	@PostConstruct
	public void init(){
		getApplication().registerForTransactionBus(this);
	}
	
	@Subscribe
	public void handleTransaction(Transaction transaction){
		
		Account account = accountrepository.findOne(transaction.getTransactionAccountId());
		Merchant merchant = merchantRepository.findOne(account.getOwnerId());
		if(merchant != null)
		{
			try {
				account.applyTransaction(transaction);
			} catch (InsufficientBalanceException e) {
				logger.error("Failed to apply transaction",e);
			}
			accountRepository.save(account);
		}
	}
	
	@Autowired
	private MerchantRepository merchantRepository;

	public MerchantRepository getMerchantRepository() {
		return merchantRepository;
	}

	public void setMerchantRepository(MerchantRepository merchantRepository) {
		this.merchantRepository = merchantRepository;
	}
	
	public Merchant getMerchant(Principal principal){
		return merchantRepository.findOne(principal.getName());
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	
}
