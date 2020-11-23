package com.spring.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends RepresentationModel<ProductDto>{
	private String name; 
	private double costPrice;
	private double sellingPrice;
	private double profitPercentage;
	
	
}
