/**
 * 
 */
package com.ecommerce.modals;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "transactiondetail")
public class TransactionDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8379353310461371289L;

	/**
	 * 
	 */
	public TransactionDetail() {
		super();
		this.transactionId = System.currentTimeMillis();
	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transaction_id", nullable = false, unique = true, updatable = false)
	private Long transactionId;

	@Column(name = "transactionDateTime", nullable = false)
	private LocalDateTime transactionDateTime;

	@Column(name = "transactionType", nullable = false)
	private String transactionType;

	@Column(name = "transactionAmount", nullable = false)
	private double transactionAmount;

	@Column(name = "walletBalance", nullable = false)
	private double walletBalance;

	@Column(name = "content")
	private String content;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true, table = "transactiondetail")
	private User user;

	/**
	 * @param transactionId
	 * @param transactionDateTime
	 * @param transactionType
	 * @param transactionAmount
	 * @param walletBalance
	 * @param content
	 * @param user
	 */
	public TransactionDetail(LocalDateTime transactionDateTime, String transactionType, double transactionAmount,
			double walletBalance, String content) {
		super();
		this.transactionId = System.currentTimeMillis();
		this.transactionDateTime = transactionDateTime;
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
		this.walletBalance = walletBalance;
		this.content = content;
	}

	/**
	 * @return the transactionId
	 */
	@Id
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the transactionDateTime
	 */
	public LocalDateTime getTransactionDateTime() {
		return transactionDateTime;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @return the transactionAmount
	 */
	public double getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @return the walletBalance
	 */
	public double getWalletBalance() {
		return walletBalance;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @param transactionDateTime the transactionDateTime to set
	 */
	public void setTransactionDateTime(LocalDateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @param walletBalance the walletBalance to set
	 */
	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "TransactionDetail [transactionId=" + transactionId + ", transactionDateTime=" + transactionDateTime
				+ ", transactionType=" + transactionType + ", transactionAmount=" + transactionAmount
				+ ", walletBalance=" + walletBalance + ", content=" + content + ", user=" + user + "]";
	}

}
