/**
 * 
 */
package com.ecommerce.modals;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "userimage")
public class UserImage {

	/**
	 * 
	 */
	public UserImage() {
		super();
		this.userImageId = System.currentTimeMillis();
	}

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_image_id", unique = true, updatable = false, nullable = false)
	private Long userImageId;

	@Lob()
	@Column(name = "user_image", columnDefinition = "LONGBLOB")
	private byte[] imageData;

	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, unique = true, table = "userimage")
	private User user;

	/**
	 * @param id
	 * @param imageData
	 * @param user
	 */
	public UserImage(byte[] imageData) {
		super();
		this.userImageId = System.currentTimeMillis();
		this.imageData = imageData;
	}

	/**
	 * @return the id
	 */

	public Long getId() {
		return userImageId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long userImageId) {
		this.userImageId = userImageId;
	}

	/**
	 * @return the imageData
	 */
	public byte[] getImageData() {
		return imageData;
	}

	/**
	 * @param imageData the imageData to set
	 */
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	/**
	 * @return the user
	 */
	@ManyToOne()
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserImage [userImageId=" + userImageId + ", imageData=" + Arrays.toString(imageData) + ", user=" + user
				+ "]";
	}

}
