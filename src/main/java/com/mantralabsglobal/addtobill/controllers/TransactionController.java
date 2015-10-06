package com.mantralabsglobal.addtobill.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.service.TransactionService;

@RestController(value="transaction")
public class TransactionController extends BaseController{
	
	TransactionService transactionService;

	@RequestMapping(method=RequestMethod.GET)
	public Transaction getTransacton(@RequestParam(value="id", required=true) String transactionId){
		return null;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Transaction createTransacton(@RequestParam(value="transaction", required=true) Transaction transaction){
		return transactionService.createTransaction(transaction);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public Transaction cancelTransacton(@RequestParam(value="id", required=true) String transactionId){
		return transactionService.cancelTransaction(transactionId);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public Transaction updateTransacton(@RequestParam(value="transaction", required=true) Transaction transaction){
		return transactionService.updateTransaction(transaction);
	}
}
