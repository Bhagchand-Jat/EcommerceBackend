package com.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.modals.User;
import com.ecommerce.modals.UserImage;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

	public Optional<UserImage> getByUser(User user);

}
