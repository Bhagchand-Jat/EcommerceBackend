/**
 * 
 */
package com.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.modals.CartItem;

/**
 * 
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
