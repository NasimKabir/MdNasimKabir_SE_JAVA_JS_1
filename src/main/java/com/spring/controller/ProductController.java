package com.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.ProductDto;
import com.spring.dto.ProductTypeDto;
import com.spring.exception.Response;
import com.spring.model.Product;
import com.spring.repository.ProductRepository;
import com.spring.service.ProductService;
import com.spring.service.ProductTypeService;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductTypeService productTypeService;
	@Autowired
	private ProductRepository productRepository;
	
	 @PostMapping("/type")
	    public Response productTypeCreated(@Valid @RequestBody ProductTypeDto productTypeDto,BindingResult result){
		  return productTypeService.productTypeInserted(productTypeDto);
	    }
	 
	 @PostMapping("/product")
	    public Response productCreated(@Valid @RequestBody ProductDto productDto,BindingResult result){
		  return productService.productInserted(productDto);
	    }
	 
	 @PutMapping("/product/{id}")
	    public Response productUpdated(@PathVariable("id") Long id,@Valid @RequestBody ProductDto productDto,BindingResult result){
		
	                return productService.updateProduct(id, productDto);
	       
	    }
	 
	 @DeleteMapping("/product/{id}")
	    public Response delete(@PathVariable("id") Long id) {
	        return productService.deleteProduct(id);
	    }

	    @GetMapping("/product/{id}")
	    public Response get(@PathVariable("id") Long id) {
	        return productService.getProduct(id);
	    }

	    @GetMapping("/product")
	    public Response getAll(Pageable pageable) {
	        return productService.getAllProduct(pageable);
	    }
	    
	    @GetMapping("/profit")
	    public List<Product> getProfit() {
	        return productRepository.profitable();
	    }
}
