package com.spring.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spring.dto.ProductDto;
import com.spring.exception.Response;
import com.spring.exception.ResponseBuilder;
import com.spring.model.Product;
import com.spring.repository.ProductRepository;
import com.spring.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Response productInserted(ProductDto productDto) {
		Product product=modelMapper.map(productDto, Product.class);
		double gainProfit=productDto.getSellingPrice()-productDto.getCostPrice();
		double profit=(gainProfit/productDto.getCostPrice())*100;
         product.setProfitPercentage(profit);
        product = productRepository.save(product);
        if (product != null) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "Product Creation Successfully", product.getName());
        }

        return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
	}

}
