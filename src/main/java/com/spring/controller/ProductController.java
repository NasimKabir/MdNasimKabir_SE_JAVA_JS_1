package com.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	    public Response productCreated(@Valid @RequestBody ProductDto productDto,BindingResult result,@RequestParam("file") MultipartFile file){
		  return productService.productInserted(productDto,file);
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
	    public ResponseEntity<CollectionModel<ProductDto>> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                @RequestParam(required = false, defaultValue = "3") Integer size,
                @RequestParam(required = false) String[] sort,
                @RequestParam(required = false, defaultValue = "asc") String dir) {

	        @SuppressWarnings("unchecked")
			CollectionModel<ProductDto> products = productService.getAllProduct(page, size, sort, dir);
	        if(products != null) {
	            return ResponseEntity.ok(products);
	        }
	        return ResponseEntity.noContent().build();
	    }
	    
	    @GetMapping("/profit")
	    public List<Product> getProfit() {
	        return productRepository.profitable();
	    }
	    
	    @GetMapping("/min")
	    public Response getMin() {
	        return productService.min();
	    }
	    
	    @GetMapping("/max")
	    public Response getMax() {
	        return productService.max();
	    }
}
