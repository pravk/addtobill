package com.mantralabsglobal.addtobill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.service.AccountService;

@RestController(value="/account")
public class AccountController extends BaseController{

	@Autowired
	private AccountService accountService;
	
	@RequestMapping(method=RequestMethod.GET)
	public Account getAccount(@RequestParam(value="id", required=true) String accountId){
		return accountService.getAccountDetails(accountId);
	}
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
}
