package com.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ApiException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.CartDao;
import com.app.dao.OrderDao;
import com.app.dao.OrderItemDao;
import com.app.dao.PaymentDao;
import com.app.dao.UserDao;
import com.app.dto.OrderDTO;
import com.app.entities.Cart;
import com.app.entities.CartItems;
import com.app.entities.Order;
import com.app.entities.OrderItem;
import com.app.entities.Payment;
import com.app.entities.Product;
import com.app.entities.User;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Autowired
	private OrderItemDao orderItemDao;
	
	@Autowired
	private CartService cartService;

	@Override
	public OrderDTO placeOrder(Long userId, Long cartId, String paymentMethod) {
		Cart cart = cartDao.findCartByUserIdAndCartId(userId, cartId);

		if (cart == null) {
			throw new ResourceNotFoundException("Invalid Cart Id ");
		}

		User user = userDao.findById(userId).orElseThrow(()->  new ResourceNotFoundException("empty"));
		
		Order order = new Order();

		order.setEmail(user.getEmail());
		order.setOrderDate(LocalDate.now());

		order.setTotalAmount(cart.getTotalPrice());
		order.setOrderStatus("Order Accepted !");

		Payment payment = new Payment();
		payment.setOrder(order);
		payment.setPaymentMethod(paymentMethod);

		payment = paymentDao.save(payment);

		order.setPayment(payment);

		Order savedOrder = orderDao.save(order);

		List<CartItems> cartItems = cart.getCartItems();

		if (cartItems.size() == 0) {
			throw new ApiException("Cart is empty");
		}

		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItems cartItem : cartItems) {
			OrderItem orderItem = new OrderItem();

			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setOrderedProductPrice(cartItem.getProductPrice());
			orderItem.setOrder(savedOrder);

			orderItems.add(orderItem);
		}

		orderItems = orderItemDao.saveAll(orderItems);

		cart.getCartItems().forEach(item -> {
			int quantity = item.getQuantity();

			Product product = item.getProduct();

			cartService.deleteCart(cartId, item.getProduct().getId());

			product.setQuantity(product.getQuantity() - quantity);
		});

		OrderDTO orderDTO = mapper.map(savedOrder, OrderDTO.class);
		
		orderItems.forEach(items -> orderDTO.getOrderItems().add(items));

		return orderDTO;
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = orderDao.findAll();
		
		return orders;
	}

	@Override
	public List<OrderDTO> getOrdersByUser(String emailId) {
		List<Order> orders = orderDao.findAllByEmail(emailId);

		List<OrderDTO> orderDTOs = orders.stream().map(order -> mapper.map(order, OrderDTO.class))
				.collect(Collectors.toList());

		if (orderDTOs.size() == 0) {
			throw new ApiException("No orders placed yet by the user with email: " + emailId);
		}

		return orderDTOs;
	}

	@Override
	public OrderDTO getOrder(String emailId, Long orderId) {
		Order order = orderDao.findOrderByEmailAndOrderId(emailId, orderId);

		if (order == null) {
			throw new ResourceNotFoundException("OrderId Invalid");
		}

		return mapper.map(order, OrderDTO.class);
	}

	@Override
	public OrderDTO updateOrder(String emailId, Long orderId, String orderStatus) {
		Order order = orderDao.findOrderByEmailAndOrderId(emailId, orderId);

		if (order == null) {
			throw new ResourceNotFoundException("OrderId Invalid");
		}

		order.setOrderStatus(orderStatus);

		return mapper.map(order, OrderDTO.class);
	}
	
	
	
	
	
	
	
	

}
