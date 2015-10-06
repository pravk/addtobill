package com.mantralabsglobal.addtobill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.service.AccountService;

@RestController(value="/account")
public class AccountController extends BaseController{

	@Autowired
	private AccountService accountService;
	
	public Account getAccount(@RequestParam(value="id", required=true) String accountId){
		return null;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
}
