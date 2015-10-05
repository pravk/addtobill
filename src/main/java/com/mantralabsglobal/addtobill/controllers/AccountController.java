package com.mantralabsglobal.addtobill.controllers;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.model.Account;

@RestController(value="/account")
public class AccountController {

	public Account getAccount(@RequestParam(value="id", required=true) String accountId){
		return null;
	}
}
