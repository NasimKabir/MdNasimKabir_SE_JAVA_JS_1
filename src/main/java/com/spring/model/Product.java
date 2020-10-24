package com.spring.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="product")
public class Product extends BaseModel{
	private static final long serialVersionUID = 1L;
	private String name; 
	private double costPrice;
	private double sellingPrice;
	private double profitPercentage;
	private boolean status;
	
	
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private ProductType productType;
	
	@OneToMany(mappedBy = "productId",fetch = FetchType.LAZY)
	private List<OrderProductItems> orderitems;
}
