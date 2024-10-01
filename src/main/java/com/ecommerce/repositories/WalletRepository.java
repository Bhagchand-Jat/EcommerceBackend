/**
 * 
 */
package com.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.modals.User;
import com.ecommerce.modals.Wallet;

/**
 * 
 */
public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Optional<Wallet> getWalletByUser(User user);

}
