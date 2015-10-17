package com.mantralabsglobal.addtobill.charge;

import java.util.Date;

import org.springframework.util.Assert;

import com.mantralabsglobal.addtobill.model.Charge;

public class ChargeBuilder {

	private Charge charge;

	private ChargeBuilder(Charge charge){
		this.charge = charge;
	}
	
	public static ChargeBuilder newCharge(){
		Charge charge= new Charge();
		charge.setChargeType(Charge.CHARGE_TYPE_CHARGE);
		charge.setChargeDate(new Date().getTime());
		return new ChargeBuilder(charge);
	}
	
	public static ChargeBuilder newRefundCharge(Charge originalCharge){
		Charge charge= new Charge();
		charge.setChargeDate(new Date().getTime());
		charge.setChargeType(Charge.CHARGE_TYPE_REFUND);
		charge.setLinkedChargeId(originalCharge.getChargeId());
		charge.setAmount(originalCharge.getAmount());
		charge.setCurrency(originalCharge.getCurrency());
		charge.setUserId(originalCharge.getUserId());
		charge.setMerchantId(originalCharge.getMerchantId());
		return new ChargeBuilder(charge);
	}
	
	public ChargeBuilder setUser(String userId){
		charge.setUserId(userId);
		return this;
	}
	
	public ChargeBuilder setMerchantId(String merchantId){
		charge.setMerchantId(merchantId);
		return this;
	}
	
	public ChargeBuilder setDescription(String description){
		charge.setDescription(description);
		return this;
	}
	
	public ChargeBuilder setStatementDescription(String description){
		charge.setStatementDescriptor(description);
		return this;
	}
	
	public ChargeBuilder setApplicationFees(double amount)
	{
		charge.setApplicationFee(amount);
		return this;
	}
	
	
	public ChargeBuilder setAmount(double amount, String currency){
		charge.setAmount(amount);
		charge.setCurrency(currency);
		return this;
	}
	
	
	public ChargeBuilder setChargeDate(long timeStamp){
		charge.setChargeDate(timeStamp);
		return this;
	}
	
	public Charge build(){
		Assert.hasText(charge.getUserId());
		Assert.hasText(charge.getMerchantId());
		Assert.isTrue(charge.getAmount()>0);
		Assert.hasText(charge.getCurrency());
		return charge;
	}

	public ChargeBuilder setStatus(String chargeStatus) {
		charge.setStatus(chargeStatus);
		return this;
	}
}
