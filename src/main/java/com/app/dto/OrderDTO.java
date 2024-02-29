package com.app.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.app.entities.OrderItem;
import com.app.entities.Payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
	

	private String email;
	
	

	private List<OrderItem> orderItems = new ArrayList<>();
	
	private LocalDate orderDate;
	
	
	private Payment payment;
	
	private double totalAmount;
	
	private String orderStatus;

}
