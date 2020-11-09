package com.spring.service;

import org.springframework.data.domain.Pageable;

import com.spring.dto.ProductDto;
import com.spring.exception.Response;

public interface ProductService {
	public Response productInserted(ProductDto productDto);

	Response updateProduct(Long id, ProductDto productDto);

	Response deleteProduct(Long id);

	Response getProduct(Long id);

	Response getAllProduct(Pageable pageable);

	Response min();

	Response max();
}
