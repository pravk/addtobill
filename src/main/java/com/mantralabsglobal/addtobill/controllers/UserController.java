package com.mantralabsglobal.addtobill.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
import com.mantralabsglobal.addtobill.model.UserAccount;
import com.mantralabsglobal.addtobill.service.AccountService;
import com.mantralabsglobal.addtobill.service.UserService;

@RestController(value="/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public User getUser(@RequestParam(value="id", required=false) String userId, @RequestParam(value="email", required=false) String email) throws ResourceNotFoundException, InvalidRequestException{
			
		User user = null;
		if(StringUtils.hasText(userId))
		{
			user = userService.getUser(userId);
		}
		else if(StringUtils.hasText(email) )
		{
			user = userService.getUserByEmail(email);
		}
		else
		{
			throw new InvalidRequestException();
		}
		
		if(user != null)
			return user;
		throw new ResourceNotFoundException(); 
	}
	
	@RequestMapping(value="/user/accounts", method=RequestMethod.GET)
	public List<UserAccount> getUserAccounts(@RequestParam(value="id") String userId) throws ResourceNotFoundException{
		return userService.getUserAccounts(userId);
	}

	@RequestMapping(value="/user/account", method=RequestMethod.GET)
	public Account getAccount(@RequestParam(value="id") String accountId) throws ResourceNotFoundException{
		Account acct = accountService.getAccountDetails(accountId);
		if(acct != null)
			return acct;
		throw new ResourceNotFoundException(); 
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public User createUser(@RequestBody User user) throws UserExistsException{
		return userService.createUser(user);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
