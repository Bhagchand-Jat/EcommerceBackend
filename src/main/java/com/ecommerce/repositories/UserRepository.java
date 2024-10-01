/**
 * 
 */
package com.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.modals.User;

/**
 * 
 */
public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByUserIdAndEmail(Long userId, String email);

	Optional<User> findByEmail(String email);

	Optional<User> findByOtpId(Long otpId);

	Optional<User> findByUserIdAndEmail(Long userId, String email);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.fullName = :fullName WHERE u.userId = :userId")
	int updateFullNameByUserId(@Param("fullName") String fullName, @Param("userId") Long userId);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.password= :password  WHERE u.userId = :userId")
	int updatePasswordByUserId(@Param("password") String password, @Param("userId") Long userId);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.fullName = :fullName, u.password= :password  WHERE u.userId = :userId")
	int updateUserByUserId(@Param("fullName") String fullName, @Param("password") String password,
			@Param("userId") Long userId);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.verified = :verified WHERE u.userId = :userId")
	int updateIsVerifiedByUserId(@Param("verified") boolean verified, @Param("userId") Long userId);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.otpId = :otpId WHERE u.userId = :userId")
	int updateOtpIdByUserId(@Param("otpId") Long otpId, @Param("userId") Long userId);

}
