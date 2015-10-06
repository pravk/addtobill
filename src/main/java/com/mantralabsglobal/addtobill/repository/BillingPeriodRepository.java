package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.BillingPeriod;

public interface BillingPeriodRepository extends CrudRepository<BillingPeriod, String> {

	BillingPeriod findByAccountIdAndStatus(String accountId, String billingPeriodStatusOpen);

}
