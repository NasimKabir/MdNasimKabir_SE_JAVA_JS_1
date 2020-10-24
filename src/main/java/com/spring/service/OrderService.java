package com.spring.service;

import javax.servlet.http.HttpServletRequest;

import com.spring.dto.OrderDto;
import com.spring.exception.Response;

public interface OrderService {
	 Response create(OrderDto orderDto, HttpServletRequest request);
	 Response totalProductsell();
}
