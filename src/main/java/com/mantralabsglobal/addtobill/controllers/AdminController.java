package com.mantralabsglobal.addtobill.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.exception.AccountExistsException;
import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.exception.MerchantDoesNotExistException;
import com.mantralabsglobal.addtobill.exception.MerchantExistsException;
import com.mantralabsglobal.addtobill.exception.ResourceNotFoundException;
import com.mantralabsglobal.addtobill.exception.UserExistsException;
import com.mantralabsglobal.addtobill.requestModel.CancelChargeRequest;
import com.mantralabsglobal.addtobill.requestModel.MerchantAccountRequest;
import com.mantralabsglobal.addtobill.requestModel.NewChargeRequest;
import com.mantralabsglobal.addtobill.requestModel.UserToken;
import com.mantralabsglobal.addtobill.responseModel.UserTokenResponse;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.service.AdminService;
import com.mantralabsglobal.addtobill.service.ChargeService;

@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ChargeService chargeService;
	
	@RequestMapping(value="admin/user", method=RequestMethod.GET)
	public User getUser(@RequestParam(value="id", required=false) String userId, @RequestParam(value="email", required=false) String email) throws ResourceNotFoundException, InvalidRequestException{
			
		User user = null;
		if(StringUtils.hasText(userId))
		{
			user = adminService.getUserById(userId);
		}
		else if(StringUtils.hasText(email) )
		{
			user = adminService.getUserByEmail(email);
		}
		else
		{
			throw new InvalidRequestException();
		}
		
		if(user != null)
			return user;
		throw new ResourceNotFoundException(); 
	}
	
	@RequestMapping(value="admin/user/authToken", method=RequestMethod.POST)
	public UserTokenResponse generateUserAuthToken(@RequestBody UserToken userToken) throws Exception{
		return adminService.generateUserAuthToken(userToken);
	}
	
	@RequestMapping(value="admin/user", method=RequestMethod.PUT)
	public User updateUser(@RequestBody User user) throws UserExistsException{
		return adminService.updateUser(user);
	}
	
	@RequestMapping(value="admin/accounts", method=RequestMethod.GET)
	public List<Account> getUserAccounts(@RequestParam(value="id") String userId) throws ResourceNotFoundException{
		return adminService.getUserAccounts(userId);
	}

	@RequestMapping(value="admin/account", method=RequestMethod.GET)
	public Account getAccount(@RequestParam(value="id") String accountId) throws ResourceNotFoundException{
		Account acct = adminService.getAccountDetails(accountId);
		if(acct != null)
			return acct;
		throw new ResourceNotFoundException(); 
	}
	
	@RequestMapping(value="admin/merchant",method=RequestMethod.POST)
	public Merchant createMerchant(@RequestBody Merchant merchant) throws MerchantExistsException, AccountExistsException, MerchantDoesNotExistException{
		return adminService.createMerchant(merchant);
	}
	
	@RequestMapping(value="admin/merchant",method=RequestMethod.PUT)
	public Merchant updateMerchant(@RequestBody Merchant merchant) throws MerchantExistsException, AccountExistsException, MerchantDoesNotExistException{
		return adminService.updateMerchant(merchant);
	}
	
	@RequestMapping(value="admin/merchant/account",method=RequestMethod.POST)
	public Account createMerchant(@RequestBody MerchantAccountRequest merchantAccountRequest) throws MerchantExistsException, AccountExistsException, MerchantDoesNotExistException{
		return adminService.createMerchantAccount(merchantAccountRequest.getMerchantId(), merchantAccountRequest.getCurrency());
	}

	@RequestMapping(value="admin/merchant",method=RequestMethod.GET)
	public Merchant createMerchant(@RequestParam(value="id", required=true) String merchantId) throws MerchantExistsException{
		return adminService.getMerchant(merchantId);
	}
	
	@RequestMapping(value="admin/charge", method=RequestMethod.POST)
	public Charge createCharge(@RequestBody NewChargeRequest chargeAttributes) throws Exception{
		return chargeService.newCharge(chargeAttributes);
	}
	
	@RequestMapping(value="admin/refund", method=RequestMethod.POST)
	public Charge cancelCharge(@RequestBody CancelChargeRequest chargeAttributes) throws Exception{
		return chargeService.refundCharge(chargeAttributes);
	}
	
	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
}
