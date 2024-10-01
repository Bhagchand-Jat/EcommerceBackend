/**
 * 
 */
package com.ecommerce.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.ecommerce.jwt.JwtTokenProvider;
import com.ecommerce.modals.Otp;
import com.ecommerce.modals.User;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.service.OtpService;
import com.ecommerce.userdetails.CustomUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author bhagc
 *
 */

@Component

public class OtpAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private JwtTokenProvider jwtTokenProvider;

	private UserRepository userRepository;
	private OtpService otpService;

	private static final Logger logger = Logger.getLogger(OtpAuthenticationFilter.class.getName());

	/**
	 * @param defaultFilterProcessesUrl
	 * @param authenticationManager
	 */

	public OtpAuthenticationFilter(AuthenticationManager authenticationManager, String filterProcessingUrl,
			JwtTokenProvider jwtTokenProvider, OtpService otpService, UserRepository userRepository) {
		this.otpService = otpService;
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.setAuthenticationManager(authenticationManager);
		this.setFilterProcessesUrl(filterProcessingUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		logger.info("attemptAuhtentication  otp");
		String otp = "";
		Long otpId = 0L;

		StringBuilder requestBody = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				requestBody.append(line);
			}

			logger.info("request body " + requestBody.toString());
			if (!requestBody.isEmpty()) {
				ObjectMapper objectMapper = new ObjectMapper();
				Otp otpData = objectMapper.readValue(requestBody.toString(), Otp.class);
				logger.info("request body user " + otpData);
				if (!otpData.getOtp().isBlank()) {
					otp = otpData.getOtp();
				}
				if (otpData.getOtpId() != null) {
					otpId = otpData.getOtpId();
				}
			}

		} catch (IOException e) {
			throw new InsufficientAuthenticationException(e.getMessage());
		}

		boolean isVerified = otpService.verifyOtp(otpId, otp);

		if (isVerified) {
			logger.info("otp verified success");

			Optional<User> userOptional = userRepository.findByOtpId(otpId);
			if (userOptional.isPresent()) {
				logger.info("user present success");
				User user = userOptional.get();

				logger.info("User login attempt" + user.getRoles());

				return getAuthenticationManager()
						.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(),
								Arrays.asList(user.getRoles().split(",")).stream()
										.map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Add "ROLE_" prefix

										.collect(Collectors.toSet())));

			} else {
				logger.info("user not present success");
				throw new InsufficientAuthenticationException("Invalid User");
			}

		} else {
			logger.info("otp not verified");
		}

		throw new InsufficientAuthenticationException("Wrong or Expired Otp");

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		logger.info("successfulAuthentication ");

		// Ensure response is not committed before redirect
		if (response.isCommitted()) {
			logger.warning("Response already committed, cannot redirect!");
			return;
		}

		Optional<User> userOptional = userRepository.findByEmail((String) authResult.getPrincipal());
		if (userOptional.isPresent()) {
			User user = userOptional.get();

			CustomUserDetail userDetail = new CustomUserDetail(user.getEmail(), user.getPassword(), user.getUserId(),
					Set.copyOf(Arrays.asList(user.getRoles().split(","))));
			String token = jwtTokenProvider.generateRefreshToken(userDetail);
			request.setAttribute("token", token);
			request.setAttribute("user", user);
			logger.info("User login success");

		}

		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		logger.info("unsuccessfulAuthentication ");

		// Log the authentication failure
		logger.warning("Authentication failed: " + failed.getMessage());

		// Set the response status to 401 Unauthorized
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		// Customize the error response
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"error\": \"Authentication failed: " + failed.getMessage() + "\"}");

	}

	@Override
	public void setRememberMeServices(RememberMeServices rememberMeServices) {

		super.setRememberMeServices(rememberMeServices);
		logger.info("Remember Me Service ");
	}

}
