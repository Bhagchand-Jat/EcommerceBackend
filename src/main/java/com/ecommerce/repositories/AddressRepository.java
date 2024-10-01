package com.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.modals.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
