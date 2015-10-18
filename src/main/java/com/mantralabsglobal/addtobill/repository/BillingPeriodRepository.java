package com.mantralabsglobal.addtobill.repository;

import java.util.Date;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.BillingPeriod;

public interface BillingPeriodRepository extends CrudRepository<BillingPeriod, String> {

	BillingPeriod findOneByAccountIdAndEndDate(String accountId, Date endDate);

	@Query("{'accountId': ?0 }")
	BillingPeriod findOneByAccountId(String accountId, Sort sort);

}
