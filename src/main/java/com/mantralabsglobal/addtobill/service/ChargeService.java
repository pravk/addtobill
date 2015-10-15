package com.mantralabsglobal.addtobill.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mantralabsglobal.addtobill.charge.ChargeProcessor;
import com.mantralabsglobal.addtobill.exception.InvalidTokenException;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.repository.ChargeRefundRequestRepository;
import com.mantralabsglobal.addtobill.repository.NewChargeRequestRepository;
import com.mantralabsglobal.addtobill.requestModel.CancelChargeRequest;
import com.mantralabsglobal.addtobill.requestModel.NewChargeRequest;
import com.mantralabsglobal.addtobill.requestModel.UserToken;

@Service
public class ChargeService extends BaseService{
	
	@Autowired
	protected NewChargeRequestRepository chargeRequestRepository;
	@Autowired
	protected ChargeRefundRequestRepository chargeRefundRequestRepository;

	@Autowired
	protected ChargeProcessor<NewChargeRequest> newChargeProcessor;
	
	@Autowired
	protected ChargeProcessor<CancelChargeRequest> refundChargeProcessor;
	
	
	public Charge newCharge(NewChargeRequest chargeRequest) throws Exception {
		
		UserToken token = resolveToken(chargeRequest.getToken());
		validateToken(token, chargeRequest);
		
		chargeRequest = chargeRequestRepository.save(chargeRequest);
		return newChargeProcessor.processRequest(chargeRequest);
	}


	protected void validateToken(UserToken token, NewChargeRequest chargeAttributes) throws InvalidTokenException {
		if(token.getExpiry() < new Date().getTime()
			|| !token.getCurrency().equals(chargeAttributes.getCurrency())
			|| token.getAmount() != chargeAttributes.getAmount()
			|| token.getMerchantId() != chargeAttributes.getDescription()
			|| token.getUserId() != chargeAttributes.getUserId()){
				throw new InvalidTokenException();
			}
	}


	public Charge refundCharge(CancelChargeRequest request) throws Exception {
		request = chargeRefundRequestRepository.save(request);
		return refundChargeProcessor.processRequest(request);
	}


	public Charge getCharge(String chargeId) {
		return chargeRepository.findOne(chargeId);
	}

}
