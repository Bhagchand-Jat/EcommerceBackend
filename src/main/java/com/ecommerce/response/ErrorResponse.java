/**
 * 
 */
package com.ecommerce.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 */
public class ErrorResponse extends Response {
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private Object error;
	private String timestamp;

	/**
	 * @param timestamp
	 * @param status
	 * @param error
	 */
	public ErrorResponse(LocalDateTime timestamp, int status, Object error) {
		super(status);

		this.timestamp = timestamp.format(formatter);
		this.error = error;
	}

	/**
	 * @param status
	 * @param error
	 */
	public ErrorResponse(int status, Object error) {
		super(status);

		this.timestamp = LocalDateTime.now().format(formatter);
		this.error = error;
	}

	/**
	 * @return the error
	 */
	public Object geterror() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void seterror(Object error) {
		this.error = error;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp.format(formatter);
	}

	@Override
	public String toString() {
		return "ErrorResponse [error=" + error + ", timestamp=" + timestamp + "]";
	}

}
