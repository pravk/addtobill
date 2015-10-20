package com.mantralabsglobal.addtobill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.User;

import com.mantralabsglobal.addtobill.repository.UserRepository;

@Service
public class AdminService extends BaseService{

	@Autowired
	private UserRepository userRepository;
	
	public User getUserByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}
	
	public User getUserById(String userId) {
		return userRepository.findOne(userId);
	}


	public List<Account> getUserAccounts(String userId) {
		return accountRepository.findAllByOwnerId(userId);
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
