package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.CartService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Aqualeaf")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	
	@PostMapping("/public/carts/{cartId}/products/{productId}/quantity/{quantity}")
	public ResponseEntity<?> addProductToCart(@PathVariable Long cartId,@PathVariable Long productId
			,@PathVariable int quantity) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addProductToCart(cartId,productId,quantity));
	}
	
	@GetMapping("/public/users/{userId}/carts/{cartId}")
	public ResponseEntity<?> getCartById(@PathVariable Long userId,@PathVariable Long cartId) {
		return ResponseEntity.status(HttpStatus.FOUND).body(cartService.getCart(userId,cartId));
	}
	
	
	@PutMapping("/public/carts/{cartId}/products/{productId}/quantity/{quantity}")
	public ResponseEntity<?> updateCart(@PathVariable Long cartId,@PathVariable Long productId,
			@PathVariable int quantity) {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.updateProductQuantityInCart(cartId,productId,quantity));
	}

	
	@DeleteMapping("/public/carts/{cartId}/product/{productId}")
	public ResponseEntity<?> deleteCart(@PathVariable Long cartId,@PathVariable Long productId) {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.deleteCart(cartId,productId));
	}
}
