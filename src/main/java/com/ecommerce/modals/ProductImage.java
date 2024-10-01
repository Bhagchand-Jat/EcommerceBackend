/**
 * 
 */
package com.ecommerce.modals;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "productimage")
public class ProductImage {

	/**
	 * 
	 */
	public ProductImage() {
		super();
		this.productImageId = System.currentTimeMillis();
	}

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_image_id", nullable = false, unique = true, updatable = false)
	private Long productImageId;

	@Lob()
	@Column(name = "product_image", columnDefinition = "LONGBLOB")
	private byte[] imageData;

	@Column(name = "is_primary", nullable = false, updatable = false)
	private boolean primary;

	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", nullable = false, updatable = false, unique = true, table = "productimage")
	private Product product;

	/**
	 * @param id
	 * @param imageData
	 * @param product
	 * @param primary
	 */
	public ProductImage(byte[] imageData, Product product, boolean primary) {
		super();
		this.productImageId = System.currentTimeMillis();
		this.imageData = imageData;
		this.product = product;
		this.primary = primary;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return productImageId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.productImageId = id;
	}

	/**
	 * @return the imageData
	 */
	public byte[] getImageData() {
		return imageData;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param imageData the imageData to set
	 */
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	/**
	 * @return the productImageId
	 */
	public Long getProductImageId() {
		return productImageId;
	}

	/**
	 * @param productImageId the productImageId to set
	 */
	public void setProductImageId(Long productImageId) {
		this.productImageId = productImageId;
	}

	/**
	 * @return the primary
	 */
	public boolean isPrimary() {
		return primary;
	}

	/**
	 * @param primary the primary to set
	 */
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ProductImage [id=" + productImageId + ", imageData=" + Arrays.toString(imageData) + ", product="
				+ product + "]";
	}

}
