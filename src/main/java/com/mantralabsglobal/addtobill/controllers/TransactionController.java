package com.mantralabsglobal.addtobill.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.business.TransactionProcessor;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.TransactionResult;

@RestController(value="transaction")
public class TransactionController extends BaseController{
	
	TransactionProcessor transactionProcessor;

	@RequestMapping(method=RequestMethod.GET)
	public Transaction getTransacton(@RequestParam(value="id", required=true) String transactionId){
		return null;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public TransactionResult createTransacton(@RequestParam(value="transaction", required=true) Transaction transaction){
		return transactionProcessor.createTransaction(transaction);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public TransactionResult deleteTransacton(@RequestParam(value="id", required=true) String transactionId){
		return transactionProcessor.deleteTransaction(transactionId);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public TransactionResult updateTransacton(@RequestParam(value="transaction", required=true) Transaction transaction){
		return transactionProcessor.updateTransaction(transaction);
	}
}
