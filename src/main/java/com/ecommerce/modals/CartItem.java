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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * 
 */
@Entity
@Table(name = "cart_item")
public class CartItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4860056090985312285L;

	/**
	 * 
	 */
	public CartItem() {
		super();
		this.itemId = System.currentTimeMillis();
		// TODO Auto-generated constructor stub
	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_id", nullable = false, unique = true, updatable = false)
	private Long itemId;

	@Min(value = 1, message = "Minimum one product is required to add")
	@Max(value = 10, message = "Maximum 10 products can be added")
	@Column(name = "quantity", nullable = false, updatable = true)
	private int quantity;

	@OneToOne(optional = false)
	@JoinColumn(name = "product_id", nullable = false, updatable = false, unique = true, table = "cart_item")
	private Product product;

	// private double price;

	/**
	 * @param itemId
	 * @param productId
	 */
	public CartItem(Product product, int quantity) {
		super();
		this.itemId = System.currentTimeMillis();
		this.quantity = quantity;
		this.product = product;

	}

	/**
	 * @return the itemId
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the productId
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "CartItem [itemId=" + itemId + ", product=" + product + "]";
	}

}
