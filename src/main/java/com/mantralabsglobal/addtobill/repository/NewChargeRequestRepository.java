package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.requestModel.NewChargeRequest;

public interface NewChargeRequestRepository  extends CrudRepository<NewChargeRequest, String> {

}
