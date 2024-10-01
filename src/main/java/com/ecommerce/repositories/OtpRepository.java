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

import com.ecommerce.modals.Otp;

/**
 * 
 */
public interface OtpRepository extends JpaRepository<Otp, Long> {
	Optional<Otp> findByOtpIdAndOtp(Long otpId, String otp);

	@Modifying
	@Transactional
	@Query("UPDATE Otp o SET o.otp = :otp WHERE o.otpId = :otpId")
	int updateOtpByOtpId(@Param("otp") String otp, @Param("otpId") Long otpId);

	@Modifying
	@Transactional
	@Query("UPDATE Otp o SET o.otp = :otp, o.expiryTime =:expiryTime WHERE o.otpId = :otpId")
	int updateOtpAndExpiryTimeByOtpId(@Param("otp") String otp, @Param("expiryTime") long expiryTime,
			@Param("otpId") Long otpId);
}
