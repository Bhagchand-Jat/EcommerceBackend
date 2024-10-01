package com.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.modals.Cart;
import com.ecommerce.modals.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findByUser(User user);

	Optional<Cart> findByUserUserId(Long userId);
}
