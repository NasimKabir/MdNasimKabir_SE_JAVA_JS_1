package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.model.OrderProductItems;

public interface OrderProductItemsRepository extends JpaRepository<OrderProductItems, Long> {
	@Query(value = "SELECT sum(p.productQuantity) FROM OrderProductItems p")
	Long quantity();
}
