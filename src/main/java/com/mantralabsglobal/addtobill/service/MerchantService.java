package com.mantralabsglobal.addtobill.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.repository.MerchantRepository;

@Service
public class MerchantService extends BaseService{

	@Autowired
	private MerchantRepository merchantRepository;

	public MerchantRepository getMerchantRepository() {
		return merchantRepository;
	}

	public void setMerchantRepository(MerchantRepository merchantRepository) {
		this.merchantRepository = merchantRepository;
	}
	
	public Merchant getMerchant(Principal principal){
		return merchantRepository.findOne(principal.getName());
	}
	
}
