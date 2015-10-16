package com.mantralabsglobal.addtobill.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Merchant {

	@Id
	private String merchantId;
	
	@JsonIgnore
	private String secretKey;
	private String merchantName;
	private String businessPrimaryColor;
	private String busiessUrl;
	private boolean chargesEnabled;
	private List<String> supportedCurrencies;
	private String defaultCurrency;
	private String displayName;
	private String email;
	private String statementDescriptor;
	private String supportEmail;
	private String supportPhone;
	private String supportUrl;
	private boolean transfersEnabled;
	private List<BankAccount> bankAccountList;
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getBusinessPrimaryColor() {
		return businessPrimaryColor;
	}
	public void setBusinessPrimaryColor(String businessPrimaryColor) {
		this.businessPrimaryColor = businessPrimaryColor;
	}
	public String getBusiessUrl() {
		return busiessUrl;
	}
	public void setBusiessUrl(String busiessUrl) {
		this.busiessUrl = busiessUrl;
	}
	public boolean getChargesEnabled() {
		return chargesEnabled;
	}
	public void setChargesEnabled(boolean chargesEnabled) {
		this.chargesEnabled = chargesEnabled;
	}
	public List<String> getSupportedCurrencies() {
		return supportedCurrencies;
	}
	public void setSupportedCurrencies(List<String> supportedCurrencies) {
		this.supportedCurrencies = supportedCurrencies;
	}
	public String getDefaultCurrency() {
		return defaultCurrency;
	}
	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatementDescriptor() {
		return statementDescriptor;
	}
	public void setStatementDescriptor(String statementDescriptor) {
		this.statementDescriptor = statementDescriptor;
	}
	public String getSupportEmail() {
		return supportEmail;
	}
	public void setSupportEmail(String supportEmail) {
		this.supportEmail = supportEmail;
	}
	public String getSupportPhone() {
		return supportPhone;
	}
	public void setSupportPhone(String supportPhone) {
		this.supportPhone = supportPhone;
	}
	public String getSupportUrl() {
		return supportUrl;
	}
	public void setSupportUrl(String supportUrl) {
		this.supportUrl = supportUrl;
	}
	public boolean isTransfersEnabled() {
		return transfersEnabled;
	}
	public void setTransfersEnabled(boolean transfersEnabled) {
		this.transfersEnabled = transfersEnabled;
	}
	public List<BankAccount> getBankAccountList() {
		return bankAccountList;
	}
	public void setBankAccountList(List<BankAccount> bankAccountList) {
		this.bankAccountList = bankAccountList;
	}
	
}
