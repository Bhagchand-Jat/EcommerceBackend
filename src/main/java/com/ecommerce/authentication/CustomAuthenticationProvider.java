/**
 * 
 */
package com.ecommerce.authentication;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.modals.User;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.userdetails.CustomUserDetailsService;

/**
 * @author bhagc
 *
 */

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = Logger.getLogger(CustomAuthenticationProvider.class.getName());
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;

	public CustomAuthenticationProvider(UserRepository repository, PasswordEncoder passwordEncoder,
			CustomUserDetailsService userDetailsService) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		logger.info("customAuthenticationProvider " + authentication.getPrincipal());
		if (authentication.getPrincipal() != null && authentication.getCredentials() != null) {

			String username = (String) authentication.getPrincipal();
			String password = (String) authentication.getCredentials();
			Optional<User> optionalUser = repository.findByEmail(username);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();

				logger.info("User is -->" + user);
				// UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (passwordEncoder.matches(password, user.getPassword()) || password.equals(user.getPassword())) {
					// authentication.setAuthenticated(true);
					// if (user.isVerified()) {
					SecurityContextHolder.getContext().setAuthentication(authentication);

					// } else {
					// logger.info("user not verified");
					// throw new InsufficientAuthenticationException("User not Exist");
					// }
				} else {
					authentication.setAuthenticated(false);
					throw new InsufficientAuthenticationException("Invalid Credentials");
					// authentication = null;
				}
			} else {
				logger.info("user not found");
				authentication.setAuthenticated(false);
				throw new InsufficientAuthenticationException("Invalid Username or Password");
				// authentication = null;

			}

			return authentication;

		}
		return null;

	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
