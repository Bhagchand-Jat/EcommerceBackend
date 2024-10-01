/**
 * 
 */
package com.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.modals.Order;

/**
 * 
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
