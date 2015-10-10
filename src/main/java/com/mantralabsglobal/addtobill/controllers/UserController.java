package com.mantralabsglobal.addtobill.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.exception.ResourceNotFoundException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.service.AccountService;
import com.mantralabsglobal.addtobill.service.UserService;

@RestController
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public User getUser(Principal principal) throws ResourceNotFoundException, InvalidRequestException{
		return userService.getUserDetails(principal);
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
	
	/*@RequestMapping(value="/user/signup", method=RequestMethod.POST)
	public User createUser(@RequestBody User user) throws UserExistsException{
		return userService.createUser(user);
	}*/
	
	@RequestMapping(value="/user/authorize", method=RequestMethod.POST)
	public String getAuthorizationCode(@RequestBody Transaction transaction ) {
		//return userService.createUser(user);
		throw new sun.reflect.generics.reflectiveObjects.NotImplementedException();
	}
	
	@RequestMapping(value="/user/transaction", method=RequestMethod.GET)
	public Transaction getTransaction(@RequestParam(value="id", required=true) String transactionId, Principal principal){
		return userService.getUserTransaction(principal, transactionId);
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
