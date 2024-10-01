/**
 * 
 */
package com.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 */
@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class UserAlreadyExistException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String message) {
		super(message);
	}

}
