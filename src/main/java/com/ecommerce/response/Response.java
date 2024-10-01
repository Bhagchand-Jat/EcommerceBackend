/**
 * 
 */
package com.ecommerce.response;

/**
 * 
 */
public class Response {

	private int status;

	/**
	 * 
	 * @param status
	 */
	public Response(int status) {
		super();

		this.status = status;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + "]";
	}

}
