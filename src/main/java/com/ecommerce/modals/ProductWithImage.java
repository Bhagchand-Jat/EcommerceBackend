/**
 * 
 */
package com.ecommerce.modals;

import java.util.List;

/**
 * 
 */
public class ProductWithImage {
	private Long productId;
	private String productName;
	private String productDescription;
	private double price;
	private List<ProductImage> images;

	private String currency;
	private String sizes;
	private String colors;
	private String productType;

	/**
	 * @param productId
	 * @param productName
	 * @param productDescription
	 * @param price
	 * @param imageData
	 * @param currency
	 * @param sizes
	 * @param colors
	 * @param productType
	 */
	public ProductWithImage(Product product, List<ProductImage> images) {
		this.productId = product.getProductId();
		this.productName = product.getProductName();
		this.productDescription = product.getProductDescription();
		this.price = product.getPrice();
		this.images = images;
		this.currency = product.getCurrency();
		this.sizes = product.getSizes();
		this.colors = product.getColors();
		this.productType = product.getProductType();
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
	 * @return the images
	 */
	public List<ProductImage> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<ProductImage> images) {
		this.images = images;
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

	@Override
	public String toString() {
		return "ProductWithImage [productId=" + productId + ", productName=" + productName + ", productDescription="
				+ productDescription + ", price=" + price + ", images=" + images + ", currency=" + currency + ", sizes="
				+ sizes + ", colors=" + colors + ", productType=" + productType + "]";
	}

}
