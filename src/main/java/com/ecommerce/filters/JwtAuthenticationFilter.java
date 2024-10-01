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
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.ecommerce.jwt.JwtTokenProvider;
import com.ecommerce.modals.User;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.userdetails.CustomUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 */
@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private JwtTokenProvider jwtTokenProvider;
	private UserRepository userRepository;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String filterProcessingUrl,
			JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		setAuthenticationManager(authenticationManager);
		setFilterProcessesUrl(filterProcessingUrl);
		this.userRepository = userRepository;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		logger.info("JwtAuthenticationFilter attempt Authentication init");
		String email = null;
		String password = null;
		StringBuilder requestBody = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				requestBody.append(line);
			}

			logger.info("request body " + requestBody.toString());
			if (!requestBody.isEmpty()) {
				ObjectMapper objectMapper = new ObjectMapper();
				User myData = objectMapper.readValue(requestBody.toString(), User.class);
				logger.info("request body user " + myData);
				if (myData.getEmail() != null) {
					email = myData.getEmail();
				}
				if (myData.getPassword() != null) {
					password = myData.getPassword();
				}
			}

		} catch (IOException e) {
			throw new InsufficientAuthenticationException(e.getMessage());
		}

		if (email == null || password == null) {
			throw new InsufficientAuthenticationException("Either Username or password does not provided or not Null");
		}
		String url = request.getRequestURI();
		String roleStr = url.contains("user") ? "USER" : "SELLER";
		logger.info("roles " + roleStr);
		Optional<User> userOptional = userRepository.findByEmail(email);
		logger.info("user " + userOptional);
		if (userOptional.isPresent() && userOptional.get().isVerified()) {

			User user = userOptional.get();
			logger.info("user exist " + user);
			Set<String> rolesSet = Arrays.stream(user.getRoles().split(",")).map(String::trim)

					.filter(role -> !role.isEmpty()).collect(Collectors.toSet());
			if (rolesSet.contains(roleStr)) {
				Set<GrantedAuthority> authorities = rolesSet.stream()
						.map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toSet());

				return getAuthenticationManager()
						.authenticate(new UsernamePasswordAuthenticationToken(email, password, authorities));
			} else {
				throw new InsufficientAuthenticationException("Access Denied");
			}
		}
		throw new InsufficientAuthenticationException("Invalid username or password");

	}

	public String getEmail(HttpServletRequest request) {

		return request.getParameter("email");
	}

	public String getPassword(HttpServletRequest request) {
		return request.getParameter("password");
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		logger.info("JwtAuthenticationFilter successfuls Authentication init");

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
		logger.warn("Authentication failed: " + failed.getMessage());

		// Set the response status to 401 Unauthorized
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		// Customize the error response
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"error\": \"Authentication failed: " + failed.getMessage() + "\"}");

		// Call the superclass method to ensure the proper handling of the exception
		// super.unsuccessfulAuthentication(request, response, failed);
	}

	@Override
	public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
		logger.info("failure handler ");

		// TODO Auto-generated method stub
		super.setAuthenticationFailureHandler(failureHandler);
	}
}
