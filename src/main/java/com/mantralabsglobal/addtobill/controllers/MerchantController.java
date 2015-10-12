package com.mantralabsglobal.addtobill.controllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.requestModel.ChargeRequest;
import com.mantralabsglobal.addtobill.service.UserAccountService;
import com.mantralabsglobal.addtobill.service.MerchantService;
import com.mantralabsglobal.addtobill.service.ChargeService;

@RestController
public class MerchantController extends BaseController{
	
	@Autowired
	MerchantService merchantService;
	@Autowired
	ChargeService transactionService;
	
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
	
	@RequestMapping(value="/merchant/charge", method=RequestMethod.POST)
	public Charge createCharge(@RequestBody ChargeRequest chargeAttributes, Principal principal) throws Exception{
		return transactionService.createCharge(chargeAttributes);
	}
	
	/*@RequestMapping(value="/merchant/transaction", method=RequestMethod.PUT)
	public TransactionResult updateTransacton(@RequestBody Transaction transaction, Principal principal) throws InvalidRequestException{
		try {
			return transactionService.updateTransaction(transaction);
		} catch (InsufficientBalanceException e) {

			return new TransactionFailureResult(e);
		}
	}*/
	
	@RequestMapping(value="/merchant/refund", method=RequestMethod.POST)
	public Charge cancelCharge(@RequestParam(value="id", required=true) String chargeId, Principal principal) throws InvalidRequestException{
		return transactionService.refundCharge(chargeId, principal.getName());
	}
	
	
}
