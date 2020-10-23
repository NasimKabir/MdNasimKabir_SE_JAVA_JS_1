package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.OrderProductItems;

public interface OrderProductItemsRepository extends JpaRepository<OrderProductItems, Long> {

}
