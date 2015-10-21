package com.mantralabsglobal.addtobill.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mantralabsglobal.addtobill.charge.ChargeProcessor;
import com.mantralabsglobal.addtobill.exception.InvalidTokenException;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.repository.ChargeRefundRequestRepository;
import com.mantralabsglobal.addtobill.repository.NewChargeRequestRepository;
import com.mantralabsglobal.addtobill.requestModel.CancelChargeRequest;
import com.mantralabsglobal.addtobill.requestModel.NewChargeRequest;
import com.mantralabsglobal.addtobill.requestModel.UserToken;

@Service
public class ChargeService extends BaseService{
	
	private static final Logger logger =LoggerFactory.getLogger(ChargeService.class);
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
		Merchant merchant = getLoggedInMerchant();
		
		validateToken(token, merchant);
		
		chargeRequest.setUserId(token.getUserId());
		chargeRequest.setAmount(token.getAmount());
		chargeRequest.setCurrency(token.getCurrency());
		chargeRequest.setMerchantId(token.getMerchantId());
		try
		{
			chargeRequest = chargeRequestRepository.save(chargeRequest);
		}
		catch(org.springframework.dao.DuplicateKeyException exception){
			logger.warn("Failed to save charge Object", exception);
			throw new InvalidTokenException("Token already used");
		}
		return newChargeProcessor.processRequest(chargeRequest);
	}


	protected void validateToken(UserToken token, Merchant merchant) throws InvalidTokenException {
		if(token.getExpiry() < new Date().getTime())
		{
			throw new InvalidTokenException("Token is expired!");
		}
		else if(!token.getMerchantId().equals(merchant.getMerchantId()))
		{
			throw new InvalidTokenException("Invalid Merchant Id");
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
