package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
