package com.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.modals.TransactionDetail;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {

}
