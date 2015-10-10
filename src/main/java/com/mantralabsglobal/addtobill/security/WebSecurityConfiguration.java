package com.mantralabsglobal.addtobill.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.MerchantRepository;
import com.mantralabsglobal.addtobill.repository.UserRepository;

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

  @Autowired
  UserRepository userRepository;
  @Autowired
  MerchantRepository merchantRepository;

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService());
  }

  @Bean
  UserDetailsService userDetailsService() {
    return new UserDetailsService() {

      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(username);
        if(user != null) {
	        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true,
	                AuthorityUtils.createAuthorityList( user.getRoles().toArray(new String[user.getRoles().size()])));
        }else {
        	Merchant merchant = merchantRepository.findOne(username);
        	if(merchant != null)
        	{
        		return new org.springframework.security.core.userdetails.User(merchant.getMerchantId(), merchant.getSecretKey(), true, true, true, true,
    	                AuthorityUtils.createAuthorityList( new String [] { User.ROLE_MERCHANT}));
        	}
        	else
        	{	
	          throw new UsernameNotFoundException("could not find the user '"
	                  + username + "'");
        	}
        }
      }
      
    };
  }
}