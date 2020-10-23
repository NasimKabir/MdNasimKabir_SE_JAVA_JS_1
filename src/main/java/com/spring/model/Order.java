package com.spring.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order extends BaseModel {
	private static final long serialVersionUID = 1L;
	private Long customerId;
	 private String totalPrice;
	@OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
	private List<OrderProductItems> items;
}
