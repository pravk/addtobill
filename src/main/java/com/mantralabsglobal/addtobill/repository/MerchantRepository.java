package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.MerchantAccount;
import com.mantralabsglobal.addtobill.model.Transaction;

public interface MerchantRepository extends CrudRepository<Merchant, String> {

	Merchant findByMerchantName(String merchantName);


}
