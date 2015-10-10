package com.mantralabsglobal.addtobill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.exception.ResourceNotFoundException;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.service.MerchantService;

@RestController
public class MerchantController extends BaseController{
	
	@Autowired
	MerchantService merchantService;
	
	@RequestMapping(value="/merchant",method=RequestMethod.GET)
	public Merchant getMerchant(@RequestParam(value="id", required=true) String merchantId) throws ResourceNotFoundException{
		Merchant merchant = merchantService.getMerchant(merchantId);
		if(merchant != null)
			return merchant;
		throw new ResourceNotFoundException(); 
	}
	
	@RequestMapping(value="/merchant",method=RequestMethod.POST)
	public Merchant createMerchant(@RequestBody Merchant merchant) throws ResourceNotFoundException{
		return merchantService.createMerchant(merchant);
	}
}
