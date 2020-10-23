package com.spring.dto;

import lombok.Data;

@Data
public class OrderProductItemsDto {
	private Long productId;
    private Long orderId;
    private int productQuantity;
}
