package com.mantralabsglobal.addtobill.service;

import java.security.Principal;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.naming.AuthenticationException;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.eventbus.Subscribe;
import com.mantralabsglobal.addtobill.Application;
import com.mantralabsglobal.addtobill.exception.AccountExistsException;
import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.exception.MerchantDoesNotExistException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.AccountBalance;
import com.mantralabsglobal.addtobill.model.CreditAccount;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
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

	public Merchant athenticateMerchant(String merchantId, String secret) throws AuthenticationException {
		Merchant merchant = merchantRepository.findOne(merchantId);
		if(merchant == null)
			throw new UsernameNotFoundException("Invalid merchant Id");
		
		if(passwordEncoder.matches(secret, merchant.getSecretKey()))
		{
			return merchant;
		}
		else
		{
			throw new AuthenticationException("Incorrect secret key");
		}
	}

	public Merchant registerMerchant(Merchant merchant) throws AccountExistsException, MerchantDoesNotExistException {
		Assert.notNull(merchant);
		
		Assert.hasText(merchant.getDefaultCurrency(), "Default currency not specified");
		Assert.hasText(merchant.getDisplayName(), "Display name not speciied");
		Assert.hasText(merchant.getMerchantName(), "Merchant name not specified");
		Assert.hasText(merchant.getSupportEmail(), "Support Email not specified");
		Assert.hasText(merchant.getSupportPhone(), "Support phone not specified");
		Assert.hasText(merchant.getBusinessUrl(), "Business URL not specified");
		Assert.hasText(merchant.getEmail(), "Email not spscified");
		
		Merchant m = merchantRepository.findOneByBusinessUrl(merchant.getBusinessUrl());
		Assert.isNull(m, "Duplicate business url");
		m = merchantRepository.findOneByMerchantName(merchant.getMerchantName());
		Assert.isNull(m, "Duplicate business name");
		
		m = merchantRepository.findOneByDisplayName(merchant.getDisplayName());
		Assert.isNull(m, "Duplicate display name");
		
		merchant.setChargesEnabled(false);
		merchant.setTransfersEnabled(false);
		merchant.setSecretKey(ObjectId.get().toString());
		merchant = merchantRepository.save(merchant);
		
		createMerchantAccount(merchant.getMerchantId(), merchant.getDefaultCurrency());
		
		return merchant;
	}
	
	public Merchant updateMerchant(Merchant merchant) {
		Assert.notNull(merchant);
		Merchant m = merchantRepository.findOne(merchant.getMerchantId());
		Assert.notNull(m, "Merchant does not exist");
		
		if(StringUtils.isNotEmpty(merchant.getBusinessPrimaryColor()))
			m.setBusinessPrimaryColor(merchant.getBusinessPrimaryColor());
		if(StringUtils.isNotEmpty(merchant.getBusinessUrl()))
			m.setBusinessUrl(merchant.getBusinessUrl());
		if(StringUtils.isNotEmpty(merchant.getDefaultCurrency()))
			m.setDefaultCurrency(merchant.getDefaultCurrency());
		if(StringUtils.isNotEmpty(merchant.getDisplayName()))
			m.setDisplayName(merchant.getDisplayName());
		if(StringUtils.isNotEmpty(merchant.getEmail()))
			m.setEmail(merchant.getEmail());
		if(StringUtils.isNotEmpty(merchant.getMerchantName()))
			m.setMerchantName(merchant.getMerchantName());
		if(StringUtils.isNotEmpty(merchant.getSecretKey()))
			m.setSecretKey(merchant.getSecretKey());
		if(StringUtils.isNotEmpty(merchant.getSupportEmail()))
			m.setSupportEmail(merchant.getSupportEmail());
		if(StringUtils.isNotEmpty(merchant.getSupportPhone()))
			m.setSupportPhone(merchant.getSupportPhone());
		if(StringUtils.isNotEmpty(merchant.getSupportUrl()))
			m.setSupportUrl(merchant.getSupportUrl());
		
		m = merchantRepository.save(m);
		return m;
	}
	
	public Account createMerchantAccount(String merchantId, String currency) throws AccountExistsException, MerchantDoesNotExistException {
		Assert.notNull(merchantId, "Invalid merchant Id");
		Assert.hasText(currency, "Invalid currency");
		Merchant m = merchantRepository.findOne(merchantId);
		if(m != null)
		{
			Account acct = accountRepository.findOneByOwnerIdAndCurrency(merchantId, currency);
			if(acct == null)
			{
				CreditAccount account = new CreditAccount();
				account.setOwnerId(m.getMerchantId());
				
				account.setLowerLimit(0);
				account.setUpperLimit(Double.MAX_VALUE);
				
				account.setAccountBalance(new AccountBalance());
				account.getAccountBalance().setBalanceUpdateDate(new Date());
				
				account.setCurrency(currency);
				
				return accountRepository.save(account);
			}
			else
			{
				throw new AccountExistsException();
			}
			
		}
		else
		{
			throw new MerchantDoesNotExistException();
		}
	}
	
}
