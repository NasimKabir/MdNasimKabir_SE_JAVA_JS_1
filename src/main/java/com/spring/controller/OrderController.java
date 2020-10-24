package com.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.OrderDto;
import com.spring.exception.Response;
import com.spring.service.OrderService;

@RestController
public class OrderController {
	private final OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	
	@PostMapping("/order")
	public Response createOrder(@Valid @RequestBody OrderDto orderDto, BindingResult result,
			HttpServletRequest request) {
		return orderService.create(orderDto, request);
	}
	
	@GetMapping("/total")
    public Response  getProfit() {
        return orderService.totalProductsell();
    }
}
