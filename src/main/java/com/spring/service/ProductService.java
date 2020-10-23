package com.spring.service;

import com.spring.dto.ProductDto;
import com.spring.exception.Response;

public interface ProductService {
public Response productInserted(ProductDto productDto);
}
