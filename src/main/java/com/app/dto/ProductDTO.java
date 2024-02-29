package com.app.dto;

import com.app.entities.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

	private Long id;
private String productName;
	
	private int quantity;
	
	private String description;
	
	private int price;
	
	private ProductCategory productCategory;
	
	private String imagePath;
	
}
