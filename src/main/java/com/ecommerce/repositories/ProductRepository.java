/**
 * 
 */
package com.ecommerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.modals.Product;
import com.ecommerce.modals.User;

/**
 * 
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> getAllBySeller(User seller, Pageable pageable);

	Page<Product> findAllByProductType(String productType, Pageable pageable);

}
