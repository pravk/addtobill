package com.mantralabsglobal.addtobill.controllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.exception.AccountExistsException;
import com.mantralabsglobal.addtobill.exception.MerchantDoesNotExistException;
import com.mantralabsglobal.addtobill.exception.MerchantExistsException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.requestModel.CancelChargeRequest;
import com.mantralabsglobal.addtobill.requestModel.MerchantAccountRequest;
import com.mantralabsglobal.addtobill.requestModel.NewChargeRequest;
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
	
	@RequestMapping(value="/merchant/register",method=RequestMethod.POST)
	public Merchant registerMerchant(Merchant merchant) throws Exception{
		return merchantService.registerMerchant(merchant);
	}
	
	@RequestMapping(value="merchant",method=RequestMethod.PUT)
	public Merchant updateMerchant(@RequestBody Merchant merchant) throws MerchantExistsException, AccountExistsException, MerchantDoesNotExistException{
		return merchantService.updateMerchant(merchant);
	}
	
	@RequestMapping(value="merchant/account",method=RequestMethod.POST)
	public Account createMerchant(@RequestBody MerchantAccountRequest merchantAccountRequest) throws MerchantExistsException, AccountExistsException, MerchantDoesNotExistException{
		return merchantService.createMerchantAccount(merchantAccountRequest.getMerchantId(), merchantAccountRequest.getCurrency());
	}
	
	@RequestMapping(value="/merchant/charge", method=RequestMethod.GET)
	public Charge getCharge(@RequestParam(value="id", required=true) String chargeId){
		return transactionService.getCharge(chargeId);
	}
	
	@RequestMapping(value="/merchant/charge", method=RequestMethod.POST)
	public Charge createCharge(@RequestBody NewChargeRequest chargeAttributes) throws Exception{
		return transactionService.newCharge(chargeAttributes);
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
	public Charge cancelCharge(@RequestBody CancelChargeRequest chargeAttributes) throws Exception{
		return transactionService.refundCharge(chargeAttributes);
	}
	
	
}
