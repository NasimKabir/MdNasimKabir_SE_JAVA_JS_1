package com.spring.service;

import com.spring.dto.ProductTypeDto;
import com.spring.exception.Response;

public interface ProductTypeService {
public Response productTypeInserted(ProductTypeDto productTypeDto);
}
