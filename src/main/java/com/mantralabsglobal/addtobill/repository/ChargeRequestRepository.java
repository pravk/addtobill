package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.requestModel.ChargeRequest;

public interface ChargeRequestRepository  extends CrudRepository<ChargeRequest, String> {

}
