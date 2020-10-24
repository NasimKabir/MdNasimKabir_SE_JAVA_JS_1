package com.spring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
         product.setStatus(true);
        product = productRepository.save(product);
        if (product != null) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "Product Creation Successfully", product.getName());
        }

        return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
	}

	@Override
	public Response updateProduct(Long id, ProductDto productDto) {
		 Product product = productRepository.findById(id).get();
	        if(product != null){
	        	modelMapper.map(productDto, Product.class);
	    		double gainProfit=productDto.getSellingPrice()-productDto.getCostPrice();
	    		double profit=(gainProfit/productDto.getCostPrice())*100;
	             product.setProfitPercentage(profit);
	            product = productRepository.save(product);
	            if(product != null){
	                return ResponseBuilder.getSuccessResponse(HttpStatus.OK, " updated Successfully", null);
	            }

	            return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error Occurs");
	        }
	        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND," not found");
	}

	@Override
	public Response deleteProduct(Long id) {
		 Product product = productRepository.findById(id).get();
	        if(product != null){
	           
	            product = productRepository.save(product);
	            if(product != null){
	                return ResponseBuilder.getSuccessResponse(HttpStatus.OK," deleted Successfully", null);
	            }
	            return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error Occurs");
	        }
	        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND," not found");
	}

	@Override
	public Response getProduct(Long id) {
		 Product product = productRepository.findById(id).get();
	        if(product != null){
	            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
	           ProductDto productDto = modelMapper.map(product, ProductDto.class);
	            if(productDto != null){
	                return ResponseBuilder.getSuccessResponse(HttpStatus.OK," retrieved Successfully", productDto);
	            }
	            return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error Occurs");
	        }
	        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND," not found");
	}

	@Override
	public Response getAllProduct(Pageable pageable) {
		Page<Product> products = productRepository.findAll(pageable);
        List<ProductDto> productDtos = this.getProducts(products,pageable);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK," retrieved Successfully", productDtos);
	}
	
	 private List<ProductDto> getProducts(Page<Product> products, Pageable pageable) {
		
		 List<ProductDto> productDtos = new ArrayList<>();
	        products.forEach(product -> {
	            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
	            ProductDto productDto = modelMapper.map(product, ProductDto.class);
	            productDtos.add(productDto);
	        });
	        return productDtos;
	}

	

}
