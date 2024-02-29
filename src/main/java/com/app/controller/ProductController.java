package com.app.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ProductDTO;
import com.app.entities.Product;
import com.app.service.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Aqualeaf")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/admin/product")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(product));
	}
	
	@GetMapping("/public/products")
	public ResponseEntity<?> getAllProducts() {
		List<ProductDTO> products = productService.getAllProducts();
		
		if(products.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.ok(products);
	}
	
	@PutMapping("/admin/products/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestBody Product product) {
		return ResponseEntity.ok(productService.updateProduct(id, product));
	}

	
	@DeleteMapping("/admin/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id){
		return ResponseEntity.ok(productService.deleteProduct(id));
	}
	
	@GetMapping("/public/products/{id}")
	public ResponseEntity<?> getProduct(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.FOUND).body(productService.getProduct(id));
	}
	
	@PutMapping("/admin/products/{id}/image")
	public ResponseEntity<?> updateProductImage(@PathVariable Long id,@RequestParam("image") MultipartFile image) throws IOException {
		return ResponseEntity.ok(productService.updateProductImage(id, image));
	}
	
}
