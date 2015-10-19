package com.mantralabsglobal.addtobill.repository;

import java.util.Date;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.BillingPeriod;

public interface BillingPeriodRepository extends CrudRepository<BillingPeriod, String> {

	//@Query("{ 'endDate' : {'$gt' : ?0, '$lt' : ?1}}")
	Iterable<BillingPeriod> findAllByEndDateBetween(Date startOfDay, Date endOfDay);


	@Query("{ 'startDate' : {'$lt' : ?0}, 'endDate' : {'$gt' : ?0}, 'accountId': ?1 }")
	BillingPeriod findOneByDateAndAccount(Date date, String acountId);
	
	@Query("{'accountId': ?0 , 'status' : 'C' }")
	BillingPeriod findOneClosedByAccountId(String accountId, Sort sort);

}
