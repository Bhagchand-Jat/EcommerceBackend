package com.ecommerce.filters;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger logger = Logger.getLogger(CustomAccessDeniedHandler.class.getName());

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		logger.info("Error " + accessDeniedException.getMessage());
		// Log the exception or perform other actions as needed

//		response.sendError(HttpServletResponse.SC_FORBIDDEN,
//				"Access Denied: You do not have permission to access this resource.");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		// Customize the error response
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"error\":" + "\"" + accessDeniedException.getLocalizedMessage() + "\"}");
	}
}
