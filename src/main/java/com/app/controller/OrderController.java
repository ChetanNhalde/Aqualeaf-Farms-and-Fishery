package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.Order;
import com.app.service.OrderService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Aqualeaf")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	
	@PostMapping("/public/users/{userId}/carts/{cartId}/payments/{paymentMethod}/order")
	public ResponseEntity<?> orderProducts(@PathVariable Long userId, @PathVariable Long cartId, @PathVariable String paymentMethod) {

		
		return ResponseEntity.status(HttpStatus.CREATED).body( orderService.placeOrder(userId, cartId, paymentMethod));
	}
	
	@GetMapping("/admin/orders")
	public ResponseEntity<?> getAllOrders() {
		
		List<Order> orders = orderService.getAllOrders();

		return  ResponseEntity.ok(orders);
	}
	
	@GetMapping("public/users/{emailId}/orders")
	public ResponseEntity<?> getOrdersByUser(@PathVariable String emailId) {
		
		return  ResponseEntity.status( HttpStatus.FOUND).body(orderService.getOrdersByUser(emailId));
	}
	
	@GetMapping("public/users/{emailId}/orders/{orderId}")
	public ResponseEntity<?> getOrderByUser(@PathVariable String emailId, @PathVariable Long orderId) {
		
		return  ResponseEntity.status( HttpStatus.FOUND).body(orderService.getOrder(emailId,orderId));
	}
	
	@PutMapping("admin/users/{emailId}/orders/{orderId}/orderStatus/{orderStatus}")
	public ResponseEntity<?> updateOrderByUser(@PathVariable String emailId, @PathVariable Long orderId, @PathVariable String orderStatus) {

		
		return  ResponseEntity.status( HttpStatus.OK).body(orderService.updateOrder(emailId, orderId, orderStatus));
	}

}
