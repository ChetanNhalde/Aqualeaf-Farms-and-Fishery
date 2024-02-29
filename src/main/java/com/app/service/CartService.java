package com.app.service;

import com.app.dto.CartDTO;

public interface CartService {

	CartDTO addProductToCart(Long cartId, Long productId, int quantity);

	CartDTO getCart(Long userId, Long cartId);

	CartDTO updateProductQuantityInCart(Long cartId, Long productId, int quantity);

	String deleteCart(Long cartId, Long productId);

	



}
