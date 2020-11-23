package com.spring.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.spring.controller.ProductController;
import com.spring.dto.ProductDto;
import com.spring.model.Product;

@Component
public class ProductAssembler implements RepresentationModelAssembler<Product, ProductDto> {
	@Override
	public ProductDto toModel(Product entity) {
		ProductDto productDto =new ProductDto(entity.getName(),entity.getCostPrice(),entity.getProfitPercentage(),entity.getSellingPrice());
		productDto.add(linkTo(methodOn(ProductController.class).get(entity.getId())).withSelfRel());
		
		return productDto;
	}

}
