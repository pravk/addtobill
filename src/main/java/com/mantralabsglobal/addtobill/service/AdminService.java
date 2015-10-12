package com.mantralabsglobal.addtobill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mantralabsglobal.addtobill.exception.MerchantExistsException;
import com.mantralabsglobal.addtobill.exception.UserExistsException;
import com.mantralabsglobal.addtobill.model.UserAccount;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.MerchantAccount;
import com.mantralabsglobal.addtobill.model.User;

import com.mantralabsglobal.addtobill.repository.UserRepository;

@Service
public class AdminService extends BaseService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserAccountService accountService;
	
	public User getUserByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}
	
	public User getUserById(String userId) {
		return userRepository.findOne(userId);
	}


	public List<UserAccount> getUserAccounts(String userId) {
		return accountRepository.findAllByUserId(userId);
	}
	
	public User createUser(User user) throws UserExistsException {
		Assert.notNull(user);
		Assert.hasText(user.getEmail());
		
		if(userRepository.findOneByEmail(user.getEmail())== null)
		{
			User savedUser = userRepository.save(user);
			accountService.createAccount(savedUser);
			return savedUser;
		}
		else
			throw new UserExistsException();
	
	}
	
	public UserAccount lockAccount(String accountId, String reason){
		UserAccount account = accountRepository.findOne(accountId);
		account.setStatus(UserAccount.ACCOUNT_STATUS_LOCKED);
		return accountRepository.save(account);
	}
	
	public UserAccount closeAccount(String accountId, String reason){
		UserAccount account = accountRepository.findOne(accountId);
		account.setStatus(UserAccount.ACCOUNT_STATUS_CLOSED);
		return accountRepository.save(account);
	}
	
	public Merchant createMerchant(Merchant merchant) throws MerchantExistsException {
		Assert.notNull(merchant);
		Assert.hasText(merchant.getMerchantName(), "Invalid merchant name");
		Merchant m = merchantRepository.findByMerchantName(merchant.getMerchantName());
		if(m == null)
		{
			merchant = merchantRepository.save(merchant);
			MerchantAccount account = new MerchantAccount();
			account.setMerchantId(merchant.getMerchantId());
			merchantAccountRepository.save(account);
			return merchant;
		}
		else
		{
			throw new MerchantExistsException();
		}
	}

	public User updateUser(User user) {
		Assert.notNull(user);
		Assert.hasText(user.getUserId());
		Assert.hasText(user.getEmail());
		Assert.hasText(user.getPassword());
		Assert.notEmpty(user.getRoles());
		return userRepository.save(user);
	}

	public Merchant getMerchant(String merchantId) {
		return merchantRepository.findOne(merchantId);
	}
}
