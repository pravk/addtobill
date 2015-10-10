package com.mantralabsglobal.addtobill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.TransactionFailureResult;
import com.mantralabsglobal.addtobill.model.TransactionResult;
import com.mantralabsglobal.addtobill.model.TransactionSuccessResult;
import com.mantralabsglobal.addtobill.service.AccountService;

@RestController()
public class AccountController extends BaseController{

	@Autowired
	private AccountService accountService;
	
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping(value="/account/transaction", method=RequestMethod.GET)
	public Transaction getTransaction(@RequestParam(value="id", required=true) String transactionId){
		return accountService.getTransaction(transactionId);
	}
	
	@RequestMapping(value="/account/transaction", method=RequestMethod.POST)
	public TransactionResult createTransacton(@RequestBody Transaction transaction) throws InvalidRequestException{
		try{
			Transaction t = accountService.createTransaction(transaction);
			if(t != null)
				return new TransactionSuccessResult(t.getTransactionId(), t.getMerchantReferenceId());
			else
				return new TransactionFailureResult("Unknow error");
		}
		catch(InsufficientBalanceException e)
		{
			return new TransactionFailureResult(e);
		}
		
	}
	
	@RequestMapping(value="/account/transaction", method=RequestMethod.DELETE)
	public TransactionResult cancelTransacton(@RequestParam(value="id", required=true) String transactionId){
			Transaction transaction = accountService.cancelTransaction(transactionId);
			if(transaction != null)
				return new TransactionSuccessResult(transaction.getTransactionId(), transaction.getMerchantReferenceId());
			else
				return new TransactionFailureResult("Unknow error");
	
	}
	
	@RequestMapping(value="/account/transaction", method=RequestMethod.PUT)
	public TransactionResult updateTransacton(@RequestBody Transaction transaction){
		//return accountService.updateTransaction(transaction);
		return null;
	}
}
