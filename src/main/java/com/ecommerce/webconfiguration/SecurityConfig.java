/**
 * 
 */
package com.ecommerce.webconfiguration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ecommerce.authentication.CustomAuthenticationProvider;
import com.ecommerce.authentication.CustomAutheticationManager;
import com.ecommerce.filters.CustomAccessDeniedHandler;
import com.ecommerce.filters.JwtAuthenticationFilter;
import com.ecommerce.filters.JwtAuthorizationFilter;
import com.ecommerce.filters.OtpAuthenticationFilter;
import com.ecommerce.jwt.JwtTokenProvider;
import com.ecommerce.repositories.TokenRepository;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.service.OtpService;
import com.ecommerce.userdetails.CustomUserDetailsService;

/**
 * 
 */

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableJpaRepositories(basePackages = "com.ecommerce.repositories")
public class SecurityConfig {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long validityInMilliseconds;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private OtpService otpService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(c -> c.disable())
				.authorizeHttpRequests(request -> request
						.requestMatchers("/ecommerce/api/user/signup", "/ecommerce/api/seller/otp/resend",
								"/ecommerce/api/user/otp/resend", "/ecommerce/api/seller/signup",
								"/ecommerce/api/user/forgot-password", "/ecommerce/api/seller/forgot-password")
						.permitAll().requestMatchers("/ecommerce/api/seller/**").hasRole("SELLER")
						.requestMatchers("/ecommerce/api/user/**").hasRole("USER")
						.requestMatchers("/ecommerce/api/password/**").hasRole("USER"))
				.addFilterAfter(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterAt(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterAt(otpAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler()));

		return http.build();
	}

	@Bean
	public String secretKey() {
		return secretKey;
	}

	@Bean
	public Long validityInMilliseconds() {
		return validityInMilliseconds;
	}

	@Bean
	public List<String> excludedUrls() {
		List<String> list = new ArrayList<>();
		list.add("/ecommerce/api/seller/otp-authenticate");
		list.add("/ecommerce/api/user/otp-authenticate");
		list.add("/ecommerce/api/user/login");
		list.add("/ecommerce/api/seller/login");
		return list;
	}

	@Bean
	public OtpAuthenticationFilter otpAuthenticationFilter() {
		return new OtpAuthenticationFilter(customAuthenticationManager(), "/ecommerce/api/user/otp-authenticate",
				jwtTokenProvider(), otpService, userRepository);
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(customAuthenticationManager(), "/ecommerce/api/user/login",
				jwtTokenProvider(), userRepository);
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtTokenProvider(), excludedUrls(), userRepository, tokenRepository);
	}

	public JwtTokenProvider jwtTokenProvider() {
		return new JwtTokenProvider(secretKey(), validityInMilliseconds());
	}

	@Bean
	@Primary
	public CustomAutheticationManager customAuthenticationManager() {
		return new CustomAutheticationManager(customAuthenticationProvider());
	}

	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider(userRepository, passwordEncoder(), userDetailsService);
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
