/**
 * 
 */
package com.ecommerce.authentication;

import java.util.logging.Logger;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * @author bhagc
 *
 */

@Service
public class CustomAutheticationManager implements AuthenticationManager {
	private static final Logger logger = Logger.getLogger(CustomAutheticationManager.class.getName());

	private CustomAuthenticationProvider provider;

	/**
	 * @param provider
	 */
	public CustomAutheticationManager(CustomAuthenticationProvider provider) {
		super();
		this.provider = provider;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("CustomAutheticationManager");
		return provider.authenticate(authentication);
	}

}
