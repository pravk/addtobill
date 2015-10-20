package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.UserMerchant;

public interface UserMerchantRepository extends CrudRepository<UserMerchant, String> {

	UserMerchant findOneByVendorUserId(String vendorUserId);

	UserMerchant findByMerchantIdAndUserId(String merchantId, String userId);

	
}
