package com.mantralabsglobal.addtobill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mantralabsglobal.addtobill.exception.MerchantExistsException;
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

	public Merchant createMerchant(Merchant merchant) throws MerchantExistsException {
		Assert.notNull(merchant);
		Assert.hasText(merchant.getMerchantName(), "Invalid merchant name");
		Merchant m = merchantRepository.findByMerchantName(merchant.getMerchantName());
		if(m == null)
		{
			return merchantRepository.save(merchant);
		}
		else
		{
			throw new MerchantExistsException();
		}
	}
	
}
