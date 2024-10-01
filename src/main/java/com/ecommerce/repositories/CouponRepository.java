/**
 * 
 */
package com.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.modals.Coupon;

/**
 * 
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {

}