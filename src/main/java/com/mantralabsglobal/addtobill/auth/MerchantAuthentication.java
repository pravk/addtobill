package com.mantralabsglobal.addtobill.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.mantralabsglobal.addtobill.model.Merchant;

public class MerchantAuthentication implements Authentication {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4565978619592860819L;
	private final Merchant merchant;
    private boolean authenticated = true;
    private User merchantAsUser;

    public MerchantAuthentication(Merchant merchant) {
        this.merchant = merchant;
        merchantAsUser = new User(merchant.getMerchantId(), merchant.getSecretKey(), getAuthorities());
    }

    @Override
    public String getName() {
        return merchant.getMerchantName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(Merchant.ROLE_MERCHANT);
    }

    @Override
    public Object getCredentials() {
        return merchant.getSecretKey();
    }

    @Override
    public User getDetails() {
        return merchantAsUser ;
    }

    @Override
    public Object getPrincipal() {
        return merchant.getMerchantId();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}