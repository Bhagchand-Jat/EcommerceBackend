/**
 * 
 */
package com.ecommerce.modals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "Otp")
public class Otp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2669431350809816881L;

	/**
	 * 
	 */
	public Otp() {
		super();
		this.otpId = System.currentTimeMillis();
	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "otp_id", nullable = false, unique = true, updatable = false)
	private Long otpId;

//	@OneToOne(optional = false)
//	@JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true, table = "otp")
//	private User user;

	private String otp;
	private long expiryTime;

	/**
	 * @param otpId
	 */
	public Otp(Long otpId) {
		super();
		this.otpId = otpId;
	}

	/**
	 * @param otp
	 * @param expiryTime
	 */
	public Otp(String otp, long expiryTime) {
		super();
		this.otpId = System.currentTimeMillis();
		this.otp = otp;
		this.expiryTime = expiryTime;
	}

	/**
	 * @return the id
	 */
	public Long getOtpId() {
		return otpId;
	}

	/**
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * @return the expiryTime
	 */
	public long getExpiryTime() {
		return expiryTime;
	}

	/**
	 * @param id the id to set
	 */
	public void setOtpId(Long otpId) {
		this.otpId = otpId;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
	 * @param expiryTime the expiryTime to set
	 */
	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public byte[] serialize() {
		Object obj = this;
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(obj);
			oos.flush();
			return bos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("Failed to serialize object", e);
		}
	}

	@Override
	public String toString() {
		return "Otp [otpId=" + otpId + ", otp=" + otp + ", expiryTime=" + expiryTime + "]";
	}

}
