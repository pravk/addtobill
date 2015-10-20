package com.mantralabsglobal.addtobill.controllers;

import java.security.Principal;
import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.exception.ResourceNotFoundException;
import com.mantralabsglobal.addtobill.exception.UserExistsException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.model.UserMerchant;
import com.mantralabsglobal.addtobill.requestModel.UserAccountRequest;
import com.mantralabsglobal.addtobill.requestModel.UserAuthRequest;
import com.mantralabsglobal.addtobill.requestModel.UserChargeTokenRequest;
import com.mantralabsglobal.addtobill.requestModel.UserMerchantRequest;
import com.mantralabsglobal.addtobill.responseModel.UserAuthToken;
import com.mantralabsglobal.addtobill.responseModel.UserChargeToken;
import com.mantralabsglobal.addtobill.service.UserAccountService;
import com.mantralabsglobal.addtobill.service.UserService;

@RestController
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserAccountService accountService;
	
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public User getUser(Principal principal) throws ResourceNotFoundException, InvalidRequestException{
		return userService.getUserDetails(principal);
	}
	
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	public User createNewUser(@RequestBody User user) throws ResourceNotFoundException, InvalidRequestException, UserExistsException{
		return userService.registerUser(user);
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public UserAuthToken loginUser(@RequestBody UserAuthRequest request) throws ResourceNotFoundException, InvalidRequestException, UserExistsException, AuthenticationException{
		return userService.loginUser(request);
	}
	
	@RequestMapping(value="/user/account", method=RequestMethod.POST)
	public Account createUser(@RequestBody UserAccountRequest user) throws UserExistsException, InvalidRequestException{
		
		return userService.createUserAccount(user.getUserId(), user.getCurrency());
	}
	
	@RequestMapping(value="/user/merchant", method=RequestMethod.POST)
	public UserMerchant createUserMerchantAssociation(@RequestBody UserMerchantRequest request) throws UserExistsException, InvalidRequestException{
		
		return userService.createUserMerchantAssociation(request);
	}
	
	@RequestMapping(value="/user/merchant", method=RequestMethod.GET)
	public UserMerchant getUserMerchantAssociation(@RequestParam(value="merchantId", required=false) String merchantId) throws UserExistsException, InvalidRequestException{
		
		return userService.fetchUserMerchantAssociation(merchantId);
	}
	
	@RequestMapping(value="user/authToken", method=RequestMethod.POST)
	public UserChargeToken generateUserAuthToken(@RequestBody UserChargeTokenRequest userToken) throws Exception{
		return userService.generateUserAuthToken(userToken);
	}
	
	@RequestMapping(value="/user/accounts", method=RequestMethod.GET)
	public List<Account> getUserAccounts(Principal principal) throws ResourceNotFoundException{
		
		return userService.getUserAccounts(principal);
	}

	@RequestMapping(value="/user/account", method=RequestMethod.GET)
	public Account getAccount(@RequestParam(value="id") String accountId) throws ResourceNotFoundException{
		Account acct = accountService.getAccountDetails(accountId);
		if(acct != null)
			return acct;
		throw new ResourceNotFoundException(); 
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
