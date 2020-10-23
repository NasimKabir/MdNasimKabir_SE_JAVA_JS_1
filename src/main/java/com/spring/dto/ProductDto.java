package com.spring.dto;

import com.spring.model.ProductType;

import lombok.Data;
@Data
public class ProductDto {
	private String name; 
	private double costPrice;
	private double sellingPrice;
	private double profitPercentage;
	private ProductType productType;
}
