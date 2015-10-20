package com.mantralabsglobal.addtobill.service;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.naming.AuthenticationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mantralabsglobal.addtobill.auth.TokenHandler;
import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.exception.UserAccountNotSetup;
import com.mantralabsglobal.addtobill.exception.UserExistsException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.model.UserMerchant;
import com.mantralabsglobal.addtobill.requestModel.UserAuthRequest;
import com.mantralabsglobal.addtobill.requestModel.UserChargeTokenRequest;
import com.mantralabsglobal.addtobill.requestModel.UserMerchantRequest;
import com.mantralabsglobal.addtobill.requestModel.UserToken;
import com.mantralabsglobal.addtobill.responseModel.UserAuthToken;
import com.mantralabsglobal.addtobill.responseModel.UserChargeToken;

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
	
	
	public UserChargeToken generateUserAuthToken(UserChargeTokenRequest userToken) throws Exception {
		Assert.notNull(userToken);
		Assert.hasText(userToken.getCurrency(), "Currency cannot be null");
		Assert.hasText(userToken.getMerchantId(),"MerchantId cannot be null");
		Assert.isTrue(userToken.getAmount()>0, "Amount needs to be greater than 0");
		
		User user = getLoggedInUser();
		Merchant merchant = merchantRepository.findOne(userToken.getMerchantId());
		if(user == null || merchant == null)
			throw new InvalidRequestException("Invalid userid and/or merchantid");
		
		if(!merchant.isChargesEnabled())
			throw new InvalidRequestException("Charge not enabled for the Merchant");
		
		UserMerchant userMerchant = userMerchantRepository.findByMerchantIdAndUserId(merchant.getMerchantId(), user.getUserId());
		
		Assert.notNull(userMerchant, "User merchant association not availale");
		
		Account userAccount = accountRepository.findOneByOwnerIdAndCurrency(user.getUserId(), userToken.getCurrency());
		if(userAccount == null)
			throw new UserAccountNotSetup();
		
		double newBalance = userAccount.getAccountBalance().getRunningBalance() + userToken.getAmount();
		if(newBalance > userAccount.getUpperLimit() || newBalance < userAccount.getLowerLimit())
			throw new InsufficientBalanceException();
		
		UserToken userToken2 = new UserToken();
		userToken2.setAmount(userToken.getAmount());
		userToken2.setCurrency(userToken.getCurrency());
		userToken2.setMerchantId(userToken.getMerchantId());
		userToken2.setUserId(user.getUserId());
		userToken2.setExpiry(DateUtils.addMinutes(new Date(), 3).getTime());
		String token = generateToken(userToken2);
		UserChargeToken response = new UserChargeToken();
		response.setToken(token);
		return response;
	}

	public UserMerchant createUserMerchantAssociation(UserMerchantRequest request) {
		Assert.notNull(request);
		Assert.hasText(request.getMerchantId(), "Merchant id cannot be empty");
		Assert.hasText(request.getVendorUserId(), "Vendor user Id cannot be empty");
		
		User user = getLoggedInUser();
		Merchant merchant = merchantRepository.findOne(request.getMerchantId());
		
		Assert.notNull(merchant, "Invalid merchant Id");
		
		UserMerchant userMerchant = userMerchantRepository.findOneByVendorUserId(request.getVendorUserId());
		Assert.isNull(userMerchant, "vendor user id is already registered");
		
		userMerchant = new UserMerchant();
		userMerchant.setMerchantId(merchant.getMerchantId());
		userMerchant.setUserId(user.getUserId());
		userMerchant.setVendorUserId(request.getVendorUserId());
		return userMerchantRepository.save(userMerchant);
	}

	public UserMerchant fetchUserMerchantAssociation(String merchantId) {
		Assert.hasText(merchantId, "Merchant id cannot be empty");
		User user = getLoggedInUser();
		return userMerchantRepository.findByMerchantIdAndUserId(merchantId, user.getUserId());
	}
		
}
