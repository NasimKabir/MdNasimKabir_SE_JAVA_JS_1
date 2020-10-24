package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.spring.model.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	@Query( value = "SELECT p.profitPercentage  FROM Product p group by p.name order by p.profitPercentage desc")
	List<Product> profitable();

}
