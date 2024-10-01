///**
// * 
// */
//package com.ecommerce.repositories;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.ecommerce.modals.Otp;
//import com.ecommerce.modals.Seller;
//
///**
// * 
// */
//public interface SellerRepository extends JpaRepository<Seller, Long> {
//	Optional<Seller> findByEmail(String email);
//
//	Optional<Seller> findByOtp(Otp otp);
//
//	Optional<Seller> findBySellerIdAndEmail(Long sellerId, String email);
//
//	@Modifying
//	@Transactional
//	@Query("UPDATE Seller u SET u.verified = :verified WHERE u.sellerId = :sellerId")
//	int updateIsVerifiedBySellerId(@Param("verified") boolean verified, @Param("sellerId") Long sellerId);
//}
