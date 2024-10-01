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
@Table(name = "coupon")
public class Coupon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8951055980214001529L;

	/**
	 * 
	 */
	public Coupon() {
		super();
	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "couponId", nullable = false, unique = true, updatable = false)
	private Long couponId;

	double maxDiscountPrice;
	double minDiscountPrice;
	double minimumPrice;
	double discountPrice;
	String description;
	LocalDateTime expiryDate;
	LocalDateTime createdDate;
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true, table = "coupon")
	private User user;

	/**
	 * @param maxDiscountPrice
	 * @param minDiscountPrice
	 * @param minimumPrice
	 * @param discountPrice
	 * @param description
	 * @param expiryDate
	 * @param createdDate
	 * @param user
	 */
	public Coupon(double maxDiscountPrice, double minDiscountPrice, double minimumPrice, double discountPrice,
			String description, LocalDateTime expiryDate, LocalDateTime createdDate) {
		super();
		this.couponId = System.currentTimeMillis();
		this.maxDiscountPrice = maxDiscountPrice;
		this.minDiscountPrice = minDiscountPrice;
		this.minimumPrice = minimumPrice;
		this.discountPrice = discountPrice;
		this.description = description;
		this.expiryDate = expiryDate;
		this.createdDate = createdDate;

	}

	/**
	 * @return the couponId
	 */
	public Long getCouponId() {
		return couponId;
	}

	/**
	 * @return the maxDiscountPrice
	 */
	public double getMaxDiscountPrice() {
		return maxDiscountPrice;
	}

	/**
	 * @return the minDiscountPrice
	 */
	public double getMinDiscountPrice() {
		return minDiscountPrice;
	}

	/**
	 * @return the minimumPrice
	 */
	public double getMinimumPrice() {
		return minimumPrice;
	}

	/**
	 * @return the discountPrice
	 */
	public double getDiscountPrice() {
		return discountPrice;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the expiryDate
	 */
	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
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
	 * @param couponId the couponId to set
	 */
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	/**
	 * @param maxDiscountPrice the maxDiscountPrice to set
	 */
	public void setMaxDiscountPrice(double maxDiscountPrice) {
		this.maxDiscountPrice = maxDiscountPrice;
	}

	/**
	 * @param minDiscountPrice the minDiscountPrice to set
	 */
	public void setMinDiscountPrice(double minDiscountPrice) {
		this.minDiscountPrice = minDiscountPrice;
	}

	/**
	 * @param minimumPrice the minimumPrice to set
	 */
	public void setMinimumPrice(double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	/**
	 * @param discountPrice the discountPrice to set
	 */
	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", maxDiscountPrice=" + maxDiscountPrice + ", minDiscountPrice="
				+ minDiscountPrice + ", minimumPrice=" + minimumPrice + ", discountPrice=" + discountPrice
				+ ", description=" + description + ", expiryDate=" + expiryDate + ", createdDate=" + createdDate
				+ ", user=" + user + "]";
	}

}
