package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exceptions.CustomException;
import com.ecommerce.modals.Cart;
import com.ecommerce.modals.CartItem;
import com.ecommerce.modals.User;
import com.ecommerce.repositories.CartItemRepository;
import com.ecommerce.repositories.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	public List<Cart> getAllCarts() {
		return cartRepository.findAll();
	}

	public Optional<Cart> getCartById(Long id) {
		return cartRepository.findById(id);
	}

	public Optional<Cart> getCartByUser(User user) {
		return cartRepository.findByUser(user);
	}

	public Cart createCart(User user) {
		Cart cart = new Cart(user);
		cart.setCartId(System.currentTimeMillis());
		return cartRepository.save(cart);
	}

	public Cart updateCart(Long id, Cart cart) {
		Optional<Cart> optionalCart = cartRepository.findById(id);
		if (optionalCart.isPresent()) {
			Cart existingCart = optionalCart.get();
			existingCart.setCartItems(cart.getCartItems());
			existingCart.setUser(cart.getUser());
			existingCart.setTotalPrice(cart.getTotalPrice());
			return cartRepository.save(existingCart);
		}
		return null;
	}

	public void deleteCart(Long id) {
		cartRepository.deleteById(id);
	}

	public void deleteCartItem(Long id) {
		cartItemRepository.deleteById(id);
	}

	public void addItemToCart(Cart cart, CartItem item) {
		cart.addItem(item);
		cartRepository.save(cart);

	}

	public void removeItemFromCart(Cart cart, CartItem item) throws CustomException {

		cart.removeItem(item);
		cartRepository.save(cart);

	}

	public Optional<Cart> getCartByUserUserId(Long userId) {

		return cartRepository.findByUserUserId(userId);
	}
}
