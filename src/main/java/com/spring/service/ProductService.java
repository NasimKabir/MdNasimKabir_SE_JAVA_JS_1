package com.spring.service;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.ProductDto;
import com.spring.exception.Response;

public interface ProductService {
	public Response productInserted(ProductDto productDto, MultipartFile file);

	Response updateProduct(Long id, ProductDto productDto);

	Response deleteProduct(Long id);

	Response getProduct(Long id);

	PagedModel getAllProduct(int page, int size, String[] sort, String dir);

	Response min();

	Response max();
}
