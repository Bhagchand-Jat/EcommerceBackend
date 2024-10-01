/**
 * 
 */
package com.ecommerce.filters;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * 
 */
public class CustomHeaderRequestWrapper extends HttpServletRequestWrapper {
	private final Map<String, String> customHeaders = new HashMap<>();

	public CustomHeaderRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getHeader(String name) {
		// Check custom headers first
		if (customHeaders.containsKey(name)) {
			return customHeaders.get(name);
		}
		// Otherwise, fall back to the original request
		return super.getHeader(name);
	}

	public void addHeader(String name, String value) {
		customHeaders.put(name, value);
	}
}
