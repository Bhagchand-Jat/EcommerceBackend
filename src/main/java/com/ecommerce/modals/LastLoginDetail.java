/**
 * 
 */
package com.ecommerce.modals;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "lastlogindetail")
public class LastLoginDetail {

	/**
	 * 
	 */
	public LastLoginDetail() {
		super();
		this.lastLoginId = System.currentTimeMillis();
	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "last_login_id", nullable = false, unique = true, updatable = false)
	private Long lastLoginId;

	@NotNull(message = "lastLoginDateTime cannot be null")
	@Column(name = "lastLoginDateTime", nullable = false)
	private LocalDateTime lastLoginDateTime;

	@NotNull(message = "lastLogoutDateTime cannot be null")
	@Column(name = "lastLogoutDateTime", nullable = false)
	private LocalDateTime lastLogoutDateTime;

	@NotNull(message = "lastLoginIp cannot be null")
	@Column(name = "lastLoginIp", nullable = false)
	private String lastLoginIp;

	@NotNull(message = "modelName cannot be null")
	@Column(name = "modelName", nullable = false)
	private String modelName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true, table = "lastlogindetail")
	private User user;

	/**
	 * @param id
	 * @param lastLoginDateTime
	 * @param lastLogoutDateTime
	 * @param lastLoginIp
	 * @param modelName
	 * @param user
	 */
	public LastLoginDetail(@NotNull(message = "lastLoginDateTime cannot be null") LocalDateTime lastLoginDateTime,
			@NotNull(message = "lastLogoutDateTime cannot be null") LocalDateTime lastLogoutDateTime,
			@NotNull(message = "lastLoginIp cannot be null") String lastLoginIp,
			@NotNull(message = "modelName cannot be null") String modelName, User user) {
		super();
		this.lastLoginId = System.currentTimeMillis();
		this.lastLoginDateTime = lastLoginDateTime;
		this.lastLogoutDateTime = lastLogoutDateTime;
		this.lastLoginIp = lastLoginIp;
		this.modelName = modelName;
		this.user = user;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return lastLoginId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long lastLoginId) {
		this.lastLoginId = lastLoginId;
	}

	/**
	 * @return the lastLoginDateTime
	 */
	public LocalDateTime getLastLoginDateTime() {
		return lastLoginDateTime;
	}

	/**
	 * @return the lastLogoutDateTime
	 */
	public LocalDateTime getLastLogoutDateTime() {
		return lastLogoutDateTime;
	}

	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @return the user
	 */

	@ManyToOne()
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	public User getUser() {
		return user;
	}

	/**
	 * @param lastLoginDateTime the lastLoginDateTime to set
	 */
	public void setLastLoginDateTime(LocalDateTime lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	/**
	 * @param lastLogoutDateTime the lastLogoutDateTime to set
	 */
	public void setLastLogoutDateTime(LocalDateTime lastLogoutDateTime) {
		this.lastLogoutDateTime = lastLogoutDateTime;
	}

	/**
	 * @param lastLoginIp the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "LastLoginDetail [lastLoginId=" + lastLoginId + ", lastLoginDateTime=" + lastLoginDateTime
				+ ", lastLogoutDateTime=" + lastLogoutDateTime + ", lastLoginIp=" + lastLoginIp + ", modelName="
				+ modelName + ", user=" + user + "]";
	}

}
