/**
 * 
 */
package com.ecommerce.modals;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Token")
public class Token {

	@Id()
	@Column(name = "token_id", nullable = false, unique = true, updatable = false)
	private Long tokenId;

	@Column(nullable = false, unique = true, length = 512)
	private String tokenValue;

	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime expiresAt;

	/**
	 * 
	 */
	public Token() {
		super();
		this.tokenId = System.currentTimeMillis();
	}

	/**
	 * @param tokenId
	 */
	public Token(Long tokenId) {
		super();
		this.tokenId = tokenId;
	}

	/**
	 * @param tokenValue
	 * @param user
	 * @param createdAt
	 * @param expiresAt
	 */
	public Token(String tokenValue, User user) {
		super();
		this.tokenId = System.currentTimeMillis();
		this.tokenValue = tokenValue;
		this.user = user;
		this.createdAt = LocalDateTime.now();
		this.expiresAt = LocalDateTime.now().withHour(23).withMinute(59).withSecond(0).withNano(0);
	}

	/**
	 * @return the tokenId
	 */
	public Long getTokenId() {
		return tokenId;
	}

	/**
	 * @param tokenId the tokenId to set
	 */
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	/**
	 * @return the tokenValue
	 */
	public String getTokenValue() {
		return tokenValue;
	}

	/**
	 * @param tokenValue the tokenValue to set
	 */
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the createdAt
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the expiresAt
	 */
	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	/**
	 * @param expiresAt the expiresAt to set
	 */
	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	@Override
	public String toString() {
		return "Token [tokenId=" + tokenId + ", tokenValue=" + tokenValue + ", user=" + user + ", createdAt="
				+ createdAt + ", expiresAt=" + expiresAt + "]";
	}

}
