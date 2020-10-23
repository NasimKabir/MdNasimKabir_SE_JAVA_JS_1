package com.spring.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spring.dto.ProductTypeDto;
import com.spring.exception.Response;
import com.spring.exception.ResponseBuilder;
import com.spring.model.ProductType;
import com.spring.repository.ProductTypeRepository;
import com.spring.service.ProductTypeService;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{
	@Autowired
	private ProductTypeRepository productTypeRepository;
	@Autowired
	private ModelMapper modelMapper;


	@Override
	public Response productTypeInserted(ProductTypeDto productTypeDto) {
		ProductType productType=modelMapper.map(productTypeDto, ProductType.class);
		
		productType = productTypeRepository.save(productType);
        if (productType != null) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "Product Creation Successfully", productType.getName());
        }

        return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
	}
}
