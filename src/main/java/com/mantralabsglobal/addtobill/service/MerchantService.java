package com.mantralabsglobal.addtobill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.repository.MerchantRepository;

@Service
public class MerchantService {

	@Autowired
	private MerchantRepository merchantRepository;

	public MerchantRepository getMerchantRepository() {
		return merchantRepository;
	}

	public void setMerchantRepository(MerchantRepository merchantRepository) {
		this.merchantRepository = merchantRepository;
	}
	
	public Merchant getMerchant(String id){
		return merchantRepository.findOne(id);
	}

	public Merchant createMerchant(Merchant merchant) {
		//TODO: Validate merchant object
		return merchantRepository.save(merchant);
	}
	
}
