package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.requestModel.PaymentRequest;

public interface PaymentRequestRepository extends CrudRepository<PaymentRequest, String>{

}
