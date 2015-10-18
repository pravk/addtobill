package com.mantralabsglobal.addtobill.service;

import java.security.Principal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.exception.UserExistsException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.User;

@Service
public class UserService  extends BaseService {

	@Autowired
	private UserAccountService accountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean signUp(User user){
		return true;
	}
	
	public boolean login(User user){
		return true;
	}

	public User getUserDetails(Principal principal) {
		return userRepository.findOneByEmail(principal.getName());
	}

	public List<Account> getUserAccounts(Principal principal) {
	 	String userId = getUserIdByEmail(principal.getName());
		return accountRepository.findAllByOwnerId(userId);
	}

	public User registerUser(User user) throws UserExistsException, InvalidRequestException {
		Assert.notNull(user);
		Assert.hasText(user.getEmail(), "Email cannot be empty");
		Assert.hasText(user.getPassword(), "Password cannot be empty");
		Assert.hasText(user.getPhoneNumber(), "Phone number cannot be empty");
		
		Assert.isNull(userRepository.findOneByEmail(user.getEmail()), "Email already in use");
		Assert.isNull(userRepository.findOneByPhoneNumber(user.getPhoneNumber()), "Phone number already in use");
		
		if(StringUtils.isEmpty(user.getDefaultCurrency())){
			user.setDefaultCurrency("inr");
		}
		
		user.setEmailVerified(false);
		user.setPhoneVerified(false);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		user = userRepository.save(user);
		
		createUserAccount(user.getUserId(), user.getDefaultCurrency());
		return user;
	}

	public Account createUserAccount(String  userId, String currency ) throws UserExistsException, InvalidRequestException {
		Assert.notNull(userId);
		User savedUser = userRepository.findOne(userId);
		if(savedUser== null)
		{
			throw new InvalidRequestException("Invalid user Id");
		}
		else if(accountService.accountExists(savedUser, currency)){
			throw new InvalidRequestException("Account already exists ");
		}
		else{
			return accountService.createAccount(savedUser, currency);
		}
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
		
}
