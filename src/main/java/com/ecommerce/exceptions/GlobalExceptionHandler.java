/**
 * 
 */
package com.ecommerce.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.ecommerce.response.ErrorResponse;
import com.ecommerce.response.Response;

import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;

/**
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserAlreadyExistException.class)
	@ResponseBody
	public ResponseEntity<Response> userAlreadyExist(UserAlreadyExistException ex) {

		return ResponseEntity
				.ok(new ErrorResponse(LocalDateTime.now(), HttpStatus.ALREADY_REPORTED.value(), ex.getMessage()));
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Response> handleMissingParams(MissingServletRequestParameterException ex) {

		return ResponseEntity.badRequest().body(new ErrorResponse(400, "Missing parameter: " + ex.getParameterName()));
	}

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<Response> handleMissingPathVariable(MissingPathVariableException ex) {
		return ResponseEntity.badRequest()
				.body(new ErrorResponse(400, "Missing path variable: " + ex.getVariableName()));
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Response> handleNoHandlerFound(NoHandlerFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse(404, "Requested URL not found: " + ex.getRequestURL()));
	}

	@ExceptionHandler(PageNotFoundException.class)
	public ResponseEntity<Response> handlePageNotFound(PageNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, ex.getLocalizedMessage()));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Response> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		// Log the exception
		System.out.println("Data integrity violation: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getLocalizedMessage()));
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public ResponseEntity<Response> userNotFound(UserNotFoundException ex) {

		return ResponseEntity.ok(new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage()));
	}

	@ExceptionHandler(CustomException.class)
//	@ResponseBody
	public ResponseEntity<Response> customException(CustomException ex, HttpStatus status) {

		return ResponseEntity.ok(new ErrorResponse(LocalDateTime.now(), status.value(), ex.getMessage()));
	}

	@ExceptionHandler(CartNotFoundException.class)
	@ResponseBody
	public ResponseEntity<Response> cartNotFound(CartNotFoundException ex) {

		return ResponseEntity.ok(new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), errors);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException ex) {

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(JwtException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> jwtException(JwtException ex) {

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(),
				ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

}
