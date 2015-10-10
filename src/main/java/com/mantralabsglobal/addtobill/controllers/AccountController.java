package com.mantralabsglobal.addtobill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.exception.AccountNotFoundException;
import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.exception.ResourceNotFoundException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.TransactionFailureResult;
import com.mantralabsglobal.addtobill.model.TransactionResult;
import com.mantralabsglobal.addtobill.model.TransactionSuccessResult;
import com.mantralabsglobal.addtobill.service.AccountService;

@RestController()
public class AccountController extends BaseController{

	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value="/account", method=RequestMethod.GET)
	public Account getAccount(@RequestParam(value="id", required=true) String accountId) throws ResourceNotFoundException{
		Account acct = accountService.getAccountDetails(accountId);
		if(acct != null)
			return acct;
		throw new ResourceNotFoundException(); 
	}
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping(value="/account/transaction", method=RequestMethod.POST)
	public TransactionResult createTransacton(@RequestBody Transaction transaction){
		try{
			Transaction t = accountService.createTransaction(transaction);
			if(t != null)
				return new TransactionSuccessResult(t.getTransactionId(), t.getVendorReferenceId());
			else
				return new TransactionFailureResult("Unknow error");
		}
		catch(InsufficientBalanceException e)
		{
			return new TransactionFailureResult(e);
		} catch (AccountNotFoundException e) {
			return new TransactionFailureResult(e);
		}
		
	}
	
	@RequestMapping(value="/account/transaction", method=RequestMethod.DELETE)
	public TransactionResult cancelTransacton(@RequestParam(value="id", required=true) String transactionId){
		//return accountService.cancelTransaction(transactionId);
		return null;
	}
	
	@RequestMapping(value="/account/transaction", method=RequestMethod.PUT)
	public TransactionResult updateTransacton(@RequestBody Transaction transaction){
		//return accountService.updateTransaction(transaction);
		return null;
	}
}
