package com.mantralabsglobal.addtobill.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.BillingPeriod;

public interface BillingPeriodRepository extends CrudRepository<BillingPeriod, String> {

	BillingPeriod findOneByAccountIdAndEndDate(String accountId, Date endDate);

}
