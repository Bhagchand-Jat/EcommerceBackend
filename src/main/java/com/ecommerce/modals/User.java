/**
 * 
 */
package com.ecommerce.modals;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * 
 */
@Entity
@Table(name = "User")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6895212256472835179L;

	/**
	 * 
	 */
	public User() {
		super();
	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", nullable = false, unique = true, updatable = false)
	private Long userId;

	@NotEmpty(message = "Full Name cannot be null or Empty", groups = { ValidationGroups.Create.class,
			ValidationGroups.Update.class })
	@Column(name = "fullName", nullable = false)
	private String fullName;

	@Email(message = "Email should be valid")
	@NotEmpty(message = "Email cannot be null or Empty", groups = ValidationGroups.Create.class)
	@Column(name = "Email", unique = true, nullable = false)
	private String email;

	@NotEmpty(message = "Password cannot be null or Empty", groups = { ValidationGroups.Create.class,
			ValidationGroups.Update.class })
	@Size(min = 8, message = "Password  should be minimum 8 length", groups = { ValidationGroups.Create.class,
			ValidationGroups.Update.class })
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "verified", nullable = false)
	private boolean verified;

	@Column(name = "created_on", nullable = false, updatable = false)
	private LocalDateTime createdOn;

	@Column(name = "roles", nullable = false, updatable = false)
	private String roles;

	@JsonIgnore

	@Column(name = "otp_id", nullable = true, updatable = false, columnDefinition = "BIGINT UNIQUE NOT NULL, FOREIGN KEY (otp_id) REFERENCES otp(id) ON DELETE CASCADE")
	private Long otpId;

	/*
	 * @OneToOne() private Otp otp;
	 * 
	 * @OneToMany(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "user_id", nullable = false, updatable = false) private
	 * Set<Address> shippingAddresses;
	 * 
	 * @OneToMany(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "user_id", nullable = false, updatable = false) private
	 * Set<Coupon> coupons;
	 * 
	 * @OneToMany(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "user_id", nullable = false, updatable = false) private
	 * Set<Order> orders;
	 */

	/**
	 * @return the otpId
	 */
	public Long getOtpId() {
		return otpId;
	}

	/**
	 * @param otpId the otpId to set
	 */
	public void setOtpId(Long otpId) {
		this.otpId = otpId;
	}

	/**
	 * @param id
	 * @param fullName
	 * @param email
	 * @param password
	 */
	public User(@NotEmpty(message = "Full Name cannot be null") String fullName,
			@NotEmpty(message = "Email cannot be null") String email,
			@NotEmpty(message = "Password cannot be null") @Size(min = 8, max = 30) String password) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.verified = false;
		this.password = password;
		this.createdOn = LocalDateTime.now();
		/*
		 * this.coupons = new HashSet<>(); this.orders = new HashSet<>();
		 * this.shippingAddresses = new HashSet<>(); this.otp = null;
		 */
	}

	/**
	 * @return the createdOn
	 */
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	/**
	 * @return the id
	 */
	@Id()
	public Long getUserId() {
		return userId;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param id the id to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the roles
	 */
	public String getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", fullName=" + fullName + ", email=" + email + ", password=" + password
				+ ", verified=" + verified + ", createdOn=" + createdOn + ", roles=" + roles + ", otpId=" + otpId + "]";
	}

	/**
	 * @param otp the otp to set
	 *//*
		 * public void setOtp(Otp otp) { this.otp = otp; }
		 * 
		 * @Override public String toString() { return "User [userId=" + userId +
		 * ", fullName=" + fullName + ", email=" + email + ", password=" + password +
		 * ", verified=" + verified + ", createdOn=" + createdOn + ", otp=" + otp +
		 * ", shippingAddresses=" + shippingAddresses + ", coupons=" + coupons +
		 * ", orders=" + orders + "]"; }
		 */

}
