package com.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spring.dto.OrderDto;
import com.spring.exception.Response;
import com.spring.exception.ResponseBuilder;
import com.spring.model.Order;
import com.spring.model.OrderProductItems;
import com.spring.model.Product;
import com.spring.repository.OrderProductItemsRepository;
import com.spring.repository.OrderRepository;
import com.spring.repository.ProductRepository;
import com.spring.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final OrderProductItemsRepository orderProductRepository;
	private final ProductRepository productRepository;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository, OrderProductItemsRepository orderProductRepository,ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.orderProductRepository = orderProductRepository;
		this.productRepository = productRepository;
	}

	@Override
	public Response create(OrderDto orderDto, HttpServletRequest request) {
		final double[] totalPrice = { 0.0 };
		orderDto.getItems().forEach(orderProductDto -> {
			Optional<Product> product = productRepository.findById(orderProductDto.getProductId());
			double productPrice = product.get().getSellingPrice() * orderProductDto.getProductQuantity();
			totalPrice[0] += productPrice;
		});
		
		Order order = new Order();
        order.setTotalPrice(String.valueOf(totalPrice[0]));
        order.setCustomerId(orderDto.getCustomerId());
        order = orderRepository.save(order);
        
        Order orderFinal=order;
		List<OrderProductItems> orderProducts = new ArrayList<>();
        orderDto.getItems().forEach(orderProductDto -> {
        	OrderProductItems orderProduct = new OrderProductItems();
            orderProduct.setOrderId(orderFinal.getCustomerId());
            orderProduct.setProductQuantity(orderProductDto.getProductQuantity());
            orderProduct.setProductId(orderProductDto.getProductId());
            orderProduct = orderProductRepository.save(orderProduct);
            orderProducts.add(orderProduct);
        });
		 

        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Order in processing. Confirm your order by complete your payment", null);
	}

	@Override
	public Response totalProductsell() {
		String sellProductNumber=String.valueOf(orderProductRepository.quantity());
		if(sellProductNumber.length()>0) {
			return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Total product selling Number is "+sellProductNumber);
		}
		return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND, "Not found");
	}

}
