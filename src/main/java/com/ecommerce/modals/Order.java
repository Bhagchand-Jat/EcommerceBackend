/**
 * 
 */
package com.ecommerce.modals;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "orders")
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4632686292942697170L;

	/**
	 * 
	 */
	public Order() {
		super();
		this.orderId = System.currentTimeMillis();
	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id", nullable = false, unique = true, updatable = false)
	private Long orderId;

	private String productId;
	private int productQuantity;

	@OneToOne(mappedBy = "address_id")
	@JoinColumn(name = "address_id", nullable = false, updatable = false, unique = true, table = "orders")
	private Address shippingAddress;

	@OneToOne(fetch = FetchType.EAGER)
	private ShippingDetail shippingDetails;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true, table = "orders")
	private User user;

	/**
	 * @param productId
	 * @param productQuantity
	 * @param shippingAddress
	 */
	public Order(String productId, int productQuantity, Address shippingAddress) {
		super();
		this.orderId = System.currentTimeMillis();
		this.productId = productId;
		this.productQuantity = productQuantity;
		this.shippingAddress = shippingAddress;

	}

	/**
	 * @return the orderId
	 */
	@Id
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @return the productQuantity
	 */
	public int getProductQuantity() {
		return productQuantity;
	}

	/**
	 * @return the shippingAddress
	 */
	public Address getShippingAddress() {
		return shippingAddress;
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
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @param productQuantity the productQuantity to set
	 */
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	/**
	 * @param shippingAddress the shippingAddress to set
	 */
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the shippingDetails
	 */
	public ShippingDetail getShippingDetails() {
		return shippingDetails;
	}

	/**
	 * @param shippingDetails the shippingDetails to set
	 */
	public void setShippingDetails(ShippingDetail shippingDetails) {
		this.shippingDetails = shippingDetails;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", productId=" + productId + ", productQuantity=" + productQuantity
				+ ", shippingAddress=" + shippingAddress + ", shippingDetails=" + shippingDetails + ", user=" + user
				+ "]";
	}

}
