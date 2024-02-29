package com.app.service;

import java.util.List;

import com.app.dto.OrderDTO;
import com.app.entities.Order;

public interface OrderService {

	OrderDTO placeOrder(Long userID, Long cartId, String paymentMethod);

	List<Order> getAllOrders();

	 List<OrderDTO>  getOrdersByUser(String emailId);

	OrderDTO getOrder(String emailId, Long orderId);

	OrderDTO updateOrder(String emailId, Long orderId, String orderStatus);

}
