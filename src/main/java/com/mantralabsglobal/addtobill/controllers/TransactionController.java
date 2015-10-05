package com.mantralabsglobal.addtobill.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.model.Transaction;

@RestController(value="transaction")
public class TransactionController {

	@RequestMapping(method=RequestMethod.GET)
	public Transaction getTransacton(@RequestParam(value="id", required=true) String transactionId){
		return null;
	}
}
