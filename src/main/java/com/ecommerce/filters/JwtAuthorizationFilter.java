/**
 * 
 */
package com.ecommerce.filters;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.exceptions.CustomException;
import com.ecommerce.exceptions.GlobalExceptionHandler;
import com.ecommerce.jwt.JwtTokenProvider;
import com.ecommerce.modals.Token;
import com.ecommerce.modals.User;
import com.ecommerce.repositories.TokenRepository;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.userdetails.CustomUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private static final Logger logger = Logger.getLogger(JwtAuthorizationFilter.class.getName());
	private final JwtTokenProvider jwtTokenProvider;
	private List<String> excludeUrls;
	private UserRepository userRepository;
	private TokenRepository tokenRepository;

	@Autowired
	private GlobalExceptionHandler exceptionHandler;

	public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider, List<String> excludeUrls,
			UserRepository userRepository, TokenRepository tokenRepository) {
		// this.exceptionHandler = exceptionHandler;
		this.jwtTokenProvider = jwtTokenProvider;
		this.excludeUrls = excludeUrls;
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			logger.info(" JwtAuthorizationFilter init");
			String header = request.getHeader("Authorization");
			if (header == null || !header.startsWith("Bearer ")) {
				logger.info("Authorization Token Required");
				header = handleCustomFilterLogic(request, response);

			}

			if (header == null) {
				logger.info("null header");
				chain.doFilter(request, response);
				return;

			}

			UsernamePasswordAuthenticationToken authentication = getAuthentication(request, header.substring(7));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			logger.info("User authenticated: " + authentication.getName());
			chain.doFilter(request, response);
		} catch (

		JwtException ex) {
			logger.info("Exception " + ex.getMessage());

			// response.setStatus(HttpStatus.NOT_FOUND.value());
			ObjectMapper objectMapper = new ObjectMapper();

			String json = objectMapper.writeValueAsString(exceptionHandler
					.customException(new CustomException(ex.getMessage()), HttpStatus.NOT_FOUND).getBody());
			response.getWriter().write(json);
			response.getWriter().flush();
		} catch (Exception ex) {
			logger.info("Exception " + ex.getMessage());

			// response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			response.getWriter().write(ex.getMessage());
			response.getWriter().flush();
		} finally {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token)
			throws JwtException {
		logger.info(" JwtAuthorizationFilter authentication");

		logger.info("jwt AuthorizationFilter token ---> " + token);
		String username = jwtTokenProvider.extractUsername(token);
		Long id = jwtTokenProvider.extractUserId(token);
		var roles = jwtTokenProvider.extractRoles(token);
		if (username != null && id != null && roles != null) {

			logger.info("Username while JwtAuthorizationFilter --> " + username + " " + id);
			logger.info("roles while JwtAuthorizationFilter --> " + roles);

			Optional<User> userOptional = userRepository.findByUserIdAndEmail(id, username);
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				CustomUserDetail userDetail = new CustomUserDetail(user.getEmail(), user.getPassword(),
						user.getUserId(), Set.copyOf(Arrays.asList(user.getRoles().split(","))));
				if (jwtTokenProvider.validateToken(token, username)) {
					logger.info("User validated in  JwtAuthorizationFilter --> ");
					request.setAttribute("user", user);
					return new UsernamePasswordAuthenticationToken(username, null, userDetail.getAuthorities());

				} else {
					logger.info("jwt not validated");
					throw new JwtException("Token Expired");

				}
			} else {
				logger.info("user not found");
				throw new JwtException("Invalid Jwt Token");
			}

		}
		throw new JwtException("Invalid Jwt Token");

	}

	private String handleCustomFilterLogic(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String path = request.getRequestURI();
		if (path.startsWith("/ecommerce/api/password/reset-password")) {
			String[] data = path.split("/");
			if (data.length > 1) {
				Long tokenId = Long.valueOf(data[data.length - 1]);
				logger.info("Token ID: " + tokenId);
				Optional<Token> tokenOptional = tokenRepository.findById(tokenId);
				if (tokenOptional.isEmpty() || tokenOptional.get().getExpiresAt().isBefore(LocalDateTime.now())) {
					throw new InsufficientAuthenticationException("Invalid or Expired Authorization token");
				}
				Token token = tokenOptional.get();
				logger.info("Token: " + token.getTokenValue());
				return "Bearer " + token.getTokenValue();

			}
			throw new InsufficientAuthenticationException("Required Authorization token");
		}
		return null;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		logger.info("Url is ---> " + request.getRequestURI());
		if (excludeUrls != null && excludeUrls.stream().anyMatch(path -> request.getRequestURI().startsWith(path))) {
			logger.info("Should not be filtered");
			return true;
		}
		return false;
	}

}
