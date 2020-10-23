package com.spring.dto;

import java.util.List;

import com.spring.model.OrderProductItems;

import lombok.Data;

@Data
public class OrderDto {
	private Long customerId;
	 private String totalPrice;
	private List<OrderProductItems> items;
}
