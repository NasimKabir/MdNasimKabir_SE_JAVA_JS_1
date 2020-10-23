package com.spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.ProductDto;
import com.spring.dto.ProductTypeDto;
import com.spring.exception.Response;
import com.spring.service.ProductService;
import com.spring.service.ProductTypeService;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductTypeService productTypeService;
	
	 @PostMapping("/type")
	    public Response productTypeCreated(@Valid @RequestBody ProductTypeDto productTypeDto,BindingResult result){
		  return productTypeService.productTypeInserted(productTypeDto);
	    }
	 
	 @PostMapping("/product")
	    public Response productCreated(@Valid @RequestBody ProductDto productDto,BindingResult result){
		  return productService.productInserted(productDto);
	    }
}
