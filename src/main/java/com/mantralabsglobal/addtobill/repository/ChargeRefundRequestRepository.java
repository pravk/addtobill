package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.requestModel.CancelChargeRequest;

public interface ChargeRefundRequestRepository  extends CrudRepository<CancelChargeRequest, String> {

}
