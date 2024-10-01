/**
 * 
 */
package com.ecommerce.modals;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "Wallet")
public class Wallet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2425641042712121301L;

	/**
	 * 
	 */
	public Wallet() {
		super();
		this.walletId = System.currentTimeMillis();
		this.balance = 0;

	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "wallet_id", nullable = false, unique = true, updatable = false)
	private Long walletId;

	private double balance;

	@OneToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true, table = "wallet")
	private User user;

	/**
	 * @return the walletId
	 */
	public Long getWalletId() {
		return walletId;
	}

	/**
	 * @param walletId the walletId to set
	 */
	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
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

	@Override
	public String toString() {
		return "Wallet [walletId=" + walletId + ", balance=" + balance + ", user=" + user + "]";
	}

}
