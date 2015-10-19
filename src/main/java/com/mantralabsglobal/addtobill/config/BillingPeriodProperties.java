package com.mantralabsglobal.addtobill.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = BillingPeriodProperties.PREFIX)
public class BillingPeriodProperties {

	public static final String PREFIX = "app.billingperiodproperties";

	private int defaultDurationDays = 14;

	public int getDefaultDurationDays() {
		return defaultDurationDays;
	}

	public void setDefaultDurationDays(int defaultDurationDays) {
		this.defaultDurationDays = defaultDurationDays;
	}
	
}
