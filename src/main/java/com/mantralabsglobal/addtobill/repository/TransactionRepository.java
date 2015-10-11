package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, String>{

	//List<Transaction> findAllByMerchantReferenceId(String merchantReferenceId);

	@Query(value = "{ 'transactionAccountId' : ?0 }", fields = "{ 'merchantReferenceId' : ?1 }")
	Transaction findOneByAccountIdAndReferenceId(String merchantAccountId, String merchantReferenceId);

	Transaction findOneByChargeIdAndTransactionAccountId(String chargeId, String merchantAccountId);

}
