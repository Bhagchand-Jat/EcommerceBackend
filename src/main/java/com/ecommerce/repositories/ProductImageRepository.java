/**
 * 
 */
package com.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.modals.Product;
import com.ecommerce.modals.ProductImage;

/**
 * 
 */
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

	Optional<ProductImage> getByProductAndPrimary(Product product, boolean primary);

	List<ProductImage> getAllByProduct(Product product);

}
