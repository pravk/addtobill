package com.mantralabsglobal.addtobill.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, String>{

	//List<Transaction> findAllByMerchantReferenceId(String merchantReferenceId);

	@Query(value = "{ 'transactionAccountId' : ?0 }", fields = "{ 'merchantReferenceId' : ?1 }")
	Transaction findOneByAccountIdAndReferenceId(String merchantAccountId, String merchantReferenceId);

	Transaction findOneByChargeIdAndTransactionAccountId(String chargeId, String merchantAccountId);

	@Query(value = "{ 'attributes.billingPeriod' : ?0 }")
	Iterable<Transaction> findByBillingPeriod(String billingPeriodId);

	Page<Transaction> findByTransactionAccountIdIn(List<String> transform, Pageable pageable);

}
