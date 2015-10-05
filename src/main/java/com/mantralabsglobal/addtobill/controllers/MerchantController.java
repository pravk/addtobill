package com.mantralabsglobal.addtobill.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.model.Merchant;

@RestController(value="/merchant")
public class MerchantController {
	
	@RequestMapping(method=RequestMethod.GET)
	public Merchant getMerchant(@RequestParam(value="id", required=true) String merchantId){
		return null;
	}
}
