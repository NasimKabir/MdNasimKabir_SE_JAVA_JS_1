package com.spring.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.spring.model.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	@Query( value = "SELECT p.profitPercentage  FROM Product p group by p.name order by p.profitPercentage desc")
	List<Product> profitable();
	
	@Query(value = "SELECT min(p.sellingPrice) FROM Product p")
	public BigDecimal min();

	@Query(value = "SELECT max(p.sellingPrice) FROM Product p")
	public BigDecimal max();

}
