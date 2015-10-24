package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.Charge;

public interface ChargeRepository  extends CrudRepository<Charge, String> {

	Charge findOneByChargeIdAndUserId(String chargeId, String userId);

	Page<Charge> findByUserId(String userId, Pageable pageable);

}
