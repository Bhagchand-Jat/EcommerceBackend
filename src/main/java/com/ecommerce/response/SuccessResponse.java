/**
 * 
 */
package com.ecommerce.response;

/**
 * 
 */
public class SuccessResponse extends Response {

	private Object success;

	/**
	 * @param status
	 * @param success
	 */
	public SuccessResponse(int status, Object success) {
		super(status);
		this.success = success;
	}

	/**
	 * @param success
	 */
	public SuccessResponse(Object success) {
		super(200);
		this.success = success;
	}

	/**
	 * @return the success
	 */
	public Object getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Object success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "SuccessResponse [success=" + success + "]";
	}

}
