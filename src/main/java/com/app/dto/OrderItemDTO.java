package com.app.dto;

import com.app.entities.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
	
	
	private Long orderItemId;

	private ProductDTO product;
	
	
	private Order order;
	
	private Integer quantity;
	private double orderedProductPrice;

}
