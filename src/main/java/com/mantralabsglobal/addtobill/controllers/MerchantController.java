package com.mantralabsglobal.addtobill.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.TransactionFailureResult;
import com.mantralabsglobal.addtobill.model.TransactionResult;
import com.mantralabsglobal.addtobill.service.UserAccountService;
import com.mantralabsglobal.addtobill.service.MerchantService;
import com.mantralabsglobal.addtobill.service.TransactionService;

@RestController
public class MerchantController extends BaseController{
	
	@Autowired
	MerchantService merchantService;
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	private UserAccountService accountService;
	
	
	public UserAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(UserAccountService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping(value="/merchant",method=RequestMethod.GET)
	public Merchant getMerchant(Principal principal){
		return merchantService.getMerchant(principal);
	}
	
	@RequestMapping(value="/merchant/transaction", method=RequestMethod.GET)
	public Transaction getTransaction(@RequestParam(value="id", required=true) String transactionId, Principal principal){
		return merchantService.getTransaction(transactionId, principal);
	}
	
	@RequestMapping(value="/merchant/transaction", method=RequestMethod.POST)
	public TransactionResult createTransacton(@RequestBody Transaction transaction, Principal principal) throws InvalidRequestException{
		try{
			return transactionService.newTransaction(transaction);
		}
		catch(InsufficientBalanceException e)
		{
			return new TransactionFailureResult(e);
		}
		
	}
	
	@RequestMapping(value="/merchant/transaction", method=RequestMethod.PUT)
	public TransactionResult updateTransacton(@RequestBody Transaction transaction, Principal principal) throws InvalidRequestException{
		try {
			return transactionService.updateTransaction(transaction);
		} catch (InsufficientBalanceException e) {

			return new TransactionFailureResult(e);
		}
	}
	
	@RequestMapping(value="/merchant/transaction", method=RequestMethod.DELETE)
	public TransactionResult cancelTransacton(@RequestParam(value="id", required=true) String transactionId, Principal principal){
		try {
			return transactionService.cancelTransaction(transactionId, principal.getName());
			
		} catch (InsufficientBalanceException e) {

			return new TransactionFailureResult(e);
		}
	}
	
	
}
