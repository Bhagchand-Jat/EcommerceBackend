/**
 * 
 */
package com.ecommerce.modals;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * 
 */
@Entity
@Table(name = "shippingdetail")
public class ShippingDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 147377285721824723L;

	/**
	 * 
	 */
	public ShippingDetail() {
		super();
		this.shippingId = System.currentTimeMillis();
	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "shipping_id", nullable = false, unique = true, updatable = false)
	private Long shippingId;

	private String shippingType;
	private double price;

	@Temporal(value = TemporalType.DATE)
	private Date estimatedArrival;

	@Temporal(value = TemporalType.DATE)
	private Date arrivalDate;

	@OneToOne(optional = false)
	@JoinColumn(name = "order_id", nullable = false, updatable = false, unique = true, table = "shippingdetail")
	private Order order;

	/**
	 * @param shippingType
	 * @param price
	 * @param estimatedArrival
	 * @param arrivalDate
	 * @param user
	 */
	public ShippingDetail(String shippingType, double price, Date estimatedArrival, Date arrivalDate) {
		super();
		this.shippingId = System.currentTimeMillis();
		this.shippingType = shippingType;
		this.price = price;
		this.estimatedArrival = estimatedArrival;
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return the shippingId
	 */
	public Long getShippingId() {
		return shippingId;
	}

	/**
	 * @return the shippingType
	 */
	public String getShippingType() {
		return shippingType;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return the estimatedArrival
	 */
	public Date getEstimatedArrival() {
		return estimatedArrival;
	}

	/**
	 * @return the arrivalDate
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param shippingId the shippingId to set
	 */
	public void setShippingId(Long shippingId) {
		this.shippingId = shippingId;
	}

	/**
	 * @param shippingType the shippingType to set
	 */
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @param estimatedArrival the estimatedArrival to set
	 */
	public void setEstimatedArrival(Date estimatedArrival) {
		this.estimatedArrival = estimatedArrival;
	}

	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "ShippingDetail [shippingId=" + shippingId + ", shippingType=" + shippingType + ", price=" + price
				+ ", estimatedArrival=" + estimatedArrival + ", arrivalDate=" + arrivalDate + ", order=" + order + "]";
	}

}
