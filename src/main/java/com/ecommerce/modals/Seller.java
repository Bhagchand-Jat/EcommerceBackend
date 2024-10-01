///**
// * 
// */
//package com.ecommerce.modals;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToOne;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//
///**
// * 
// */
//@Entity
//public class Seller {
//
//	/**
//	 * 
//	 */
//	public Seller() {
//		super();
//		this.sellerId = System.currentTimeMillis();
//	}
//
//	@Id()
////	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "seller_id", nullable = false, unique = true, updatable = false)
//	private Long sellerId;
//
//	@Column(name = "sellerName", nullable = false)
//	private String sellerName;
//
//	@NotNull(message = "Email cannot be null")
//	@Column(name = "Email", unique = true, nullable = false)
//	private String email;
//
//	@NotNull(message = "Password cannot be null")
//	@Size(min = 8)
//	@Column(name = "password", nullable = false)
//	private String password;
//
//	@Column(name = "verified", nullable = false)
//	private boolean verified;
//
//	@Column(name = "created_on", nullable = false, updatable = false)
//	private LocalDateTime createdOn;
//
//	@Column(name = "store_name", nullable = false)
//	private String storeName;
//
//	@Column(name = "contact_number", nullable = false)
//	private String contactNumber;
//
//	@OneToOne(optional = true)
//	@JoinColumn(name = "otp_id", nullable = true, updatable = false, unique = true, table = "seller")
//	private Otp otp;
//
//	@Column(name = "roles", nullable = false, updatable = false)
//	private String roles;
//
//	@OneToMany(fetch = FetchType.EAGER)
//	List<Product> products;
//
//	/**
//	 * @param sellerName
//	 * @param email
//	 * @param password
//	 * @param storeName
//	 * @param contactNumber
//	 */
//	public Seller(String sellerName, @NotNull(message = "Email cannot be null") String email,
//			@NotNull(message = "Password cannot be null") @Size(min = 8) String password, String storeName,
//			String contactNumber) {
//		super();
//		this.sellerId = System.currentTimeMillis();
//		this.sellerName = sellerName;
//		this.email = email;
//		this.password = password;
//		this.storeName = storeName;
//		this.contactNumber = contactNumber;
//		this.createdOn = LocalDateTime.now();
//	}
//
//	/**
//	 * @return the sellerId
//	 */
//	public Long getSellerId() {
//		return sellerId;
//	}
//
//	/**
//	 * @return the sellerName
//	 */
//	public String getSellerName() {
//		return sellerName;
//	}
//
//	/**
//	 * @return the products
//	 */
//	public List<Product> getProducts() {
//		return products;
//	}
//
//	/**
//	 * @return the email
//	 */
//	public String getEmail() {
//		return email;
//	}
//
//	/**
//	 * @param email the email to set
//	 */
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	/**
//	 * @return the password
//	 */
//	public String getPassword() {
//		return password;
//	}
//
//	/**
//	 * @param password the password to set
//	 */
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	/**
//	 * @return the isVerified
//	 */
//	public boolean isVerified() {
//		return verified;
//	}
//
//	/**
//	 * @param isVerified the isVerified to set
//	 */
//	public void setVerified(boolean verified) {
//		this.verified = verified;
//	}
//
//	/**
//	 * @return the storeName
//	 */
//	public String getStoreName() {
//		return storeName;
//	}
//
//	/**
//	 * @param storeName the storeName to set
//	 */
//	public void setStoreName(String storeName) {
//		this.storeName = storeName;
//	}
//
//	/**
//	 * @return the contactNumber
//	 */
//	public String getContactNumber() {
//		return contactNumber;
//	}
//
//	/**
//	 * @param contactNumber the contactNumber to set
//	 */
//	public void setContactNumber(String contactNumber) {
//		this.contactNumber = contactNumber;
//	}
//
//	/**
//	 * @return the createdOn
//	 */
//	public LocalDateTime getCreatedOn() {
//		return createdOn;
//	}
//
//	/**
//	 * @param createdOn the createdOn to set
//	 */
//	public void setCreatedOn(LocalDateTime createdOn) {
//		this.createdOn = createdOn;
//	}
//
//	/**
//	 * @param products the products to set
//	 */
//	public void setProducts(List<Product> products) {
//		this.products = products;
//	}
//
//	public void addProduct(Product product) {
//		this.products.add(product);
//		product.setSeller(this);
//	}
//
//	public void removeProduct(Product product) {
//		this.products.removeIf(p -> p.getProductId() == product.getProductId());
//		product.setSeller(null);
//	}
//
//	/**
//	 * @param sellerId the sellerId to set
//	 */
//	public void setSellerId(Long sellerId) {
//		this.sellerId = sellerId;
//	}
//
//	/**
//	 * @param sellerName the sellerName to set
//	 */
//	public void setSellerName(String sellerName) {
//		this.sellerName = sellerName;
//	}
//
//	/**
//	 * @return the otp
//	 */
//	public Otp getOtp() {
//		return otp;
//	}
//
//	/**
//	 * @param otp the otp to set
//	 */
//	public void setOtp(Otp otp) {
//		this.otp = otp;
//	}
//
//	/**
//	 * @return the roles
//	 */
//	public String getRoles() {
//		return roles;
//	}
//
//	/**
//	 * @param roles the roles to set
//	 */
//	public void setRoles(String roles) {
//		this.roles = roles;
//	}
//
//	@Override
//	public String toString() {
//		return "Seller [sellerId=" + sellerId + ", sellerName=" + sellerName + ", email=" + email + ", password="
//				+ password + ", verified=" + verified + ", createdOn=" + createdOn + ", storeName=" + storeName
//				+ ", contactNumber=" + contactNumber + ", otp=" + otp + ", products=" + products + ", roles=" + roles
//				+ "]";
//	}
//
//}
