/**
 * 
 */
package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.repositories.UserRepository;

/**
 * 
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public int updateIsVerifiedByUserId(boolean isVerified, Long userId) {
		return userRepository.updateIsVerifiedByUserId(isVerified, userId);
	}

}
