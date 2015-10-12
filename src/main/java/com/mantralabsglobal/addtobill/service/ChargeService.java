package com.mantralabsglobal.addtobill.service;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mantralabsglobal.addtobill.Application;
import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.exception.InvalidTokenException;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.model.MerchantAccount;
import com.mantralabsglobal.addtobill.repository.ChargeRepository;
import com.mantralabsglobal.addtobill.repository.ChargeRequestRepository;
import com.mantralabsglobal.addtobill.requestModel.ChargeRequest;
import com.mantralabsglobal.addtobill.requestModel.UserToken;

@Service
public class ChargeService extends BaseService{
	
	@Autowired
	protected ChargeRequestRepository chargeRequestRepository;
	
	@Autowired
	protected ChargeRepository chargeRepository;
	
	public Charge createCharge(ChargeRequest chargeRequest) throws Exception {
		
		UserToken token = resolveToken(chargeRequest.getToken());
		validateToken(token, chargeRequest);
		
		Charge charge = clone(chargeRequest,Charge.class);
		charge.setStatus(Charge.CHARGE_STATUS_RECORDED);
		charge.setChargeType(Charge.CHARGE_TYPE_CHARGE);
		charge.setApplicationFee(0);
		charge.setChargeId(ObjectId.get().toString());
		charge.setPaid(false);
		charge = chargeRepository.save(charge);
		Application.postChargeEvent(charge);
		return charge;
	}


	protected void validateToken(UserToken token, ChargeRequest chargeAttributes) throws InvalidTokenException {
		if(token.getExpiry() < new Date().getTime()
			|| !token.getCurrency().equals(chargeAttributes.getCurrency())
			|| token.getAmount() != chargeAttributes.getAmount()
			|| token.getMerchantId() != chargeAttributes.getDescription()
			|| token.getUserId() != chargeAttributes.getUserId()){
				throw new InvalidTokenException();
			}
	}


	public Charge refundCharge(String chargeId, String merchantId) throws InvalidRequestException {
		Charge charge = chargeRepository.findOne(chargeId);
		MerchantAccount merchantAccount = merchantAccountRepository.findOneByMerchantIdAndCurrency(merchantId, charge.getCurrency());
		if(charge.getMerchantAccountId().equals(merchantAccount.getMerchantAccountId())){
			Charge refundCharge = clone(charge, Charge.class);
			refundCharge.setChargeId(ObjectId.get().toString());
			refundCharge.setLinkedChargeId(chargeId);
			refundCharge.setChargeType(Charge.CHARGE_TYPE_REFUND);
			refundCharge.setApplicationFee(0);
			refundCharge= chargeRepository.save(refundCharge);
			Application.postChargeEvent(refundCharge);
			return charge;
		}
		else
		{
			throw new InvalidRequestException();
		}
	}

}
