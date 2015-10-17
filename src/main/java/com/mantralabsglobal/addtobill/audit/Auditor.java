package com.mantralabsglobal.addtobill.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component(value="mongoAuditor")
public class Auditor implements AuditorAware<String> {

	
    @Override
    public String getCurrentAuditor() {
    	if(SecurityContextHolder.getContext().getAuthentication() != null)
    		return SecurityContextHolder.getContext().getAuthentication().getName();
    	else 
    		return "system";
    }

}