package com.mantralabsglobal.addtobill.service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.naming.AuthenticationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mantralabsglobal.addtobill.auth.TokenHandler;
import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.exception.UserExistsException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.requestModel.UserAuthRequest;
import com.mantralabsglobal.addtobill.responseModel.UserAuthToken;

@Service
public class UserService  extends BaseService implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
	private UserAccountService accountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenHandler tokenHandler;
	
	public boolean signUp(User user){
		return true;
	}
	
	public boolean login(User user){
		return true;
	}

	public User getUserDetails(Principal principal) {
		return userRepository.findOneByEmail(principal.getName());
	}

	@Override
	public final org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException {
	    User user = userRepository.findOneByEmail(username); 
		if (user == null) {
	         throw new UsernameNotFoundException("user not found");
	    }
		if(user.getRoles() == null)
			user.setRoles(Arrays.asList(User.ROLE_USER));
	    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true,
                AuthorityUtils.createAuthorityList( user.getRoles().toArray(new String[user.getRoles().size()])));
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
		user.setRoles(Arrays.asList(User.ROLE_USER));
		
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

	public UserAuthToken loginUser(UserAuthRequest request) throws AuthenticationException {
		User user = userRepository.findOneByEmail(request.getUserName());
		if(user == null)
			throw new UsernameNotFoundException(request.getUserName());
		
		if(passwordEncoder.matches(request.getPassword(), user.getPassword()))
		{
			UserAuthToken authToken = new UserAuthToken();
			if(user.getRoles() == null)
				user.setRoles(Arrays.asList(User.ROLE_USER));
			String token = tokenHandler.createTokenForUser( new org.springframework.security.core.userdetails.User(request.getUserName(), user.getPassword(), AuthorityUtils.createAuthorityList( user.getRoles().toArray(new String[user.getRoles().size()])) ) );
			authToken.setToken(token);
			return  authToken;
		}
		else
		{
			throw new AuthenticationException("Incorrect password");
		}
	}
		
}
