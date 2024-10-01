/**
 * 
 */
package com.ecommerce.modals;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "Cart")
public class Cart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6768440912015057312L;

	/**
	 * 
	 */
	public Cart() {
		super();
		this.cartId = System.currentTimeMillis();

	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cart_id", nullable = false, unique = true, updatable = false)
	private Long cartId;

	@OneToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true, table = "cart")
	@JsonIgnore
	private User user;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cart_id")
	private Set<CartItem> cartItems = new HashSet<>();

	@Column(name = "total_price", nullable = false)
	@Formula("SELECT SUM(ci.product.price) FROM cart_item ci WHERE ci.cart_id = cart_id) as total_price")
	private double totalPrice;

	/**
	 * @param user
	 * @param totalPrice
	 */
	public Cart(User user) {
		super();
		this.cartId = System.currentTimeMillis();
		this.user = user;
		this.totalPrice = 0.0;
	}

	/**
	 * @return the cartId
	 */
	public Long getCartId() {
		return cartId;
	}

	/**
	 * @param cartId the cartId to set
	 */
	public void setCartId(Long cartId) {
		this.cartId = cartId;
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
	 * @return the cartItems
	 */
	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	/**
	 * @param cartItem the cartItem to set
	 */
	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	/**
	 * @return the totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void addItem(CartItem item) {
		this.cartItems.add(item);
		// this.totalPrice += item.getPrice();
	}

	public void removeItem(CartItem item) {
		this.cartItems.remove(item);
		// this.totalPrice -= item.getPrice();
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", user=" + user + ", cartItems=" + cartItems + ", totalPrice=" + totalPrice
				+ "]";
	}

}
