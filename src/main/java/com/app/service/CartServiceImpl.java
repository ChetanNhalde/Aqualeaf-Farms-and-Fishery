package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ApiException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.CartDao;
import com.app.dao.CartItemDao;
import com.app.dao.ProductDao;
import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.entities.Cart;
import com.app.entities.CartItems;
import com.app.entities.Product;



@Service
@Transactional
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CartItemDao cartItemDao;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CartDTO addProductToCart(Long cartId, Long productId, int quantity) {
		
		Cart cart = cartDao.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Invalid cart Id"));
		Product product = productDao.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Invalid Product Id!!!"));
		CartItems cartItem = cartItemDao.findCartItemByProductIdAndCartId(cartId,productId);
//		if(cartItem == null) {
//			throw new ApiException("Product already exists in cart");
//		}
//		
//		if(product.getQuantity() == 0) {
//			throw new ApiException("Product is not available");
//		}
		
		CartItems newCartItem = new CartItems();
		newCartItem.setProduct(product);
		newCartItem.setProductPrice(product.getPrice());
		newCartItem.setQuantity(quantity);
		newCartItem.setCart(cart);
		
		
		cartItemDao.save(newCartItem);
		
		product.setQuantity(product.getQuantity() - quantity);
		cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));
		CartDTO cartDTO = mapper.map(cart, CartDTO.class);
		
		List<ProductDTO> productDTOs = cart.getCartItems().stream()
				.map(p -> mapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());
		cartDTO.setProducts(productDTOs);
		return cartDTO;
		
		
	}

	@Override
	public CartDTO getCart(Long userId, Long cartId) {
		Cart cart = cartDao.findCartByUserIdAndCartId(userId,cartId);
		
//		if(cart == null) {
//			throw new ResourceNotFoundException("Invalid cart Id");
//		}
		
		CartDTO cartDTO = mapper.map(cart, CartDTO.class);
		List<ProductDTO> productDTOs = cart.getCartItems().stream()
				.map(p -> mapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());
		
		cartDTO.setProducts(productDTOs);
		return cartDTO;
	}

	@Override
	public CartDTO updateProductQuantityInCart(Long cartId, Long productId, int quantity) {
		Cart cart = cartDao.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid cart Id"));

		Product product = productDao.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Product Id!!!"));

		if (product.getQuantity() == 0) {
			throw new ApiException(product.getProductName() + " is not available");
		}

		if (product.getQuantity() < quantity) {
			throw new ApiException("Please, make an order of the " + product.getProductName()
					+ " less than or equal to the quantity " + product.getQuantity() + ".");
		}

		CartItems cartItem = cartItemDao.findCartItemByProductIdAndCartId(cartId, productId);

		if (cartItem == null) {
			throw new ApiException("Product " + product.getProductName() + " not available in the cart!!!");
		}

		double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

		product.setQuantity(product.getQuantity() + cartItem.getQuantity() - quantity);

		cartItem.setProductPrice(product.getPrice());
		cartItem.setQuantity(quantity);

		cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * quantity));

		cartItem = cartItemDao.save(cartItem);

		CartDTO cartDTO = mapper.map(cart, CartDTO.class);

		List<ProductDTO> productDTOs = cart.getCartItems().stream()
				.map(p -> mapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

		cartDTO.setProducts(productDTOs);

		return cartDTO;
	}

	@Override
	public String deleteCart(Long cartId, Long productId) {
		
		Cart cart = cartDao.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid cart Id"));
		
		CartItems cartItem = cartItemDao.findCartItemByProductIdAndCartId(cartId, productId);

		if (cartItem == null) {
			throw new ResourceNotFoundException("empty");
		}

		cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

		Product product = cartItem.getProduct();
		product.setQuantity(product.getQuantity() + cartItem.getQuantity());

		cartItemDao.deleteCartItemByProductIdAndCartId(cartId, productId);

		return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
		
	}
	
	
	
	
	
	
	
	
	

}
