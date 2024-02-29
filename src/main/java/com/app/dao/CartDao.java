package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Cart;

public interface CartDao extends JpaRepository<Cart, Long> {

	@Query("SELECT c FROM Cart c WHERE c.user.id = ?1 AND c.id = ?2")
	Cart findCartByUserIdAndCartId(Long userId, Long cartId);

	@Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.id = ?1")
	List<Cart> findCartsByProductId(Long productId);
	

	

}
