package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.BillPayment;

public interface BillPaymentRepository extends CrudRepository<BillPayment, String> {

}
