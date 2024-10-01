/**
 * 
 */
package com.ecommerce.modals;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

/**
 * 
 */

@Entity
@Table(name = "product")
@SecondaryTable(name = "productimage", pkJoinColumns = @PrimaryKeyJoinColumn(name = "product_id"))
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1002326049939403457L;

	/**
	 * 
	 */
	public Product() {
		super();
		this.productId = System.currentTimeMillis();
	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id", nullable = false, unique = true, updatable = false)
	private Long productId;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "product_description")
	private String productDescription;

	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "seller_id", nullable = false, updatable = false, unique = false, table = "product")
//	private Seller seller;
	private User seller;

//	@Column(name = "primary_image")
//	private String primaryImage;

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "product_quantity", nullable = false)
	private int productQuantity;

	@Column(name = "currency", nullable = false)
	private String currency;

	// @Size(min = 255)
	@Column(columnDefinition = "varchar(255) default null")
	private String sizes;

	// @Size(min = 255)
	@Column(columnDefinition = "varchar(255) default null")
	private String colors;

	@Column(name = "product_type")
	private String productType;

//	@JsonIgnore
//	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//	Set<ProductImage> images;

	/**
	 * @param productName
	 * @param productDescription
	 * @param seller
	 * @param primaryImage
	 * @param price
	 * @param productQuantity
	 * @param currency
	 * @param sizes
	 * @param colors
	 * @param productType
	 * @param images
	 */
	public Product(String productName, String productDescription, User seller, double price, int productQuantity,
			String currency, @Size(min = 255) String sizes, @Size(min = 255) String colors, String productType) {
		super();
		this.productId = System.currentTimeMillis();
		this.productName = productName;
		this.productDescription = productDescription;
		this.seller = seller;
		this.price = price;
		this.productQuantity = productQuantity;
		this.currency = currency;
		this.sizes = sizes;
		this.colors = colors;
		this.productType = productType;
	}

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}

	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	/**
	 * @return the seller
	 */
	public User getSeller() {
		return seller;
	}

	/**
	 * @param seller the seller to set
	 */
	public void setSeller(User seller) {
		this.seller = seller;
	}

	/*	*//**
			 * @return the primaryImage
			 */
	/*
	 * public String getPrimaryImage() { return primaryImage; }
	 * 
	 *//**
		 * @param primaryImage the primaryImage to set
		 *//*
			 * public void setPrimaryImage(String primaryImage) { this.primaryImage =
			 * primaryImage; }
			 */

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the productQuantity
	 */
	public int getProductQuantity() {
		return productQuantity;
	}

	/**
	 * @param productQuantity the productQuantity to set
	 */
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the sizes
	 */
	public String getSizes() {
		return sizes;
	}

	/**
	 * @param sizes the sizes to set
	 */
	public void setSizes(String sizes) {
		this.sizes = sizes;
	}

	/**
	 * @return the colors
	 */
	public String getColors() {
		return colors;
	}

	/**
	 * @param colors the colors to set
	 */
	public void setColors(String colors) {
		this.colors = colors;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

//	/**
//	 * @return the images
//	 */
//	public Set<ProductImage> getImages() {
//		return images;
//	}
//
//	/**
//	 * @param images the images to set
//	 */
//	public void setImages(Set<ProductImage> images) {
//		this.images = images;
//	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productDescription="
				+ productDescription + ", seller_id=" + seller + ", price=" + price + ", productQuantity="
				+ productQuantity + ", currency=" + currency + ", sizes=" + sizes + ", colors=" + colors
				+ ", productType=" + productType + "]";
	}

}
