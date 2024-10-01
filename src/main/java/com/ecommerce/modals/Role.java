/**
 * 
 */
package com.ecommerce.modals;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "Role")
public class Role {

	@Id()
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id", nullable = false, unique = true, updatable = false)
	private Long roleId;

	@Column(name = "role", unique = true, nullable = false)
	private String role;

	/**
	* 
	*/
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param role
	 */
	public Role(String role) {
		super();
		this.roleId = System.currentTimeMillis();
		this.role = role;
	}

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", role=" + role + "]";
	}

}
