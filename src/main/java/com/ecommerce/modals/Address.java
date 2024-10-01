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
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * 
 */
@Entity
@Table(name = "Address")
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2014951080173490L;

	/**
	 * 
	 */
	public Address() {
		super();
		this.addressId = System.currentTimeMillis();
	}

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "address_id", nullable = false, unique = true, updatable = false)
	private Long addressId;

	@NotEmpty(message = "State cannot be null", groups = ValidationGroups.Create.class)
	@Column(name = "state", nullable = false)
	private String state;
	@NotEmpty(message = "country cannot be null", groups = ValidationGroups.Create.class)
	@Column(name = "country", nullable = false)
	private String country;

	@NotEmpty(message = "District cannot be null", groups = ValidationGroups.Create.class)
	@Column(name = "district", nullable = false)
	private String district;

	@Min(value = 100000, message = "Pincode lenght should be 6")
	@Max(value = 999999, message = "Pincode lenght should be 6")
	@Column(name = "pincode", nullable = false)
	int pinCode;

	@NotEmpty(message = "Address Type cannot be null", groups = ValidationGroups.Create.class)
	@Column(name = "addressType", nullable = false)
	String addressType;

	@NotEmpty(message = "Full Address cannot be null", groups = ValidationGroups.Create.class)
	@Column(name = "fullAddress", nullable = false)
	String fullAddress;

	@NotEmpty(message = "Name cannot be null", groups = ValidationGroups.Create.class)
	@Column(name = "Name", nullable = false)
	String name;

	@Size(min = 10, max = 13, message = "Enter valid phoneNo", groups = ValidationGroups.Create.class)
	@Column(name = "phoneNo", nullable = false)
	String phoneNo;

	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	/**
	 * @param addressId
	 * @param state
	 * @param country
	 * @param district
	 * @param pinCode
	 * @param addressType
	 * @param fullAddress
	 * @param name
	 * @param phoneNo
	 * @param user
	 */
	public Address(@NotEmpty(message = "State cannot be null") String state,
			@NotEmpty(message = "country cannot be null") String country,
			@NotEmpty(message = "District cannot be null") String district,
			@Min(value = 100000, message = "Pincode lenght should be 6") @Max(value = 999999, message = "Pincode lenght should be 6") int pinCode,
			@NotEmpty(message = "Address Type cannot be null") String addressType,
			@NotEmpty(message = "Full Address cannot be null") String fullAddress,
			@NotEmpty(message = "Name cannot be null") String name,
			@Size(min = 10, max = 13, message = "Enter valid phoneNo") String phoneNo) {
		super();
		this.addressId = System.currentTimeMillis();
		this.state = state;
		this.country = country;
		this.district = district;
		this.pinCode = pinCode;
		this.addressType = addressType;
		this.fullAddress = fullAddress;
		this.name = name;
		this.phoneNo = phoneNo;

	}

	/**
	 * @return the addressId
	 */
	public Long getAddressId() {
		return addressId;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @return the pinCode
	 */
	public int getPinCode() {
		return pinCode;
	}

	/**
	 * @return the addressType
	 */
	public String getAddressType() {
		return addressType;
	}

	/**
	 * @return the fullAddress
	 */
	public String getFullAddress() {
		return fullAddress;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @return the user
	 */

	public User getUser() {
		return user;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @param addressType the addressType to set
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	/**
	 * @param fullAddress the fullAddress to set
	 */
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", state=" + state + ", country=" + country + ", district="
				+ district + ", pinCode=" + pinCode + ", addressType=" + addressType + ", fullAddress=" + fullAddress
				+ ", name=" + name + ", phoneNo=" + phoneNo + ", user=" + user + "]";
	}

}
