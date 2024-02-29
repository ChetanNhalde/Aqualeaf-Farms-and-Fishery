package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApiResponse;
import com.app.dto.ProductDTO;
import com.app.entities.Product;

public interface ProductService {

	ProductDTO addProduct(Product product);

	List<ProductDTO> getAllProducts();

	ProductDTO updateProduct(Long id, Product product);

	ApiResponse deleteProduct(Long id);

	ProductDTO getProduct(Long id);

	ProductDTO updateProductImage(Long id, MultipartFile image) throws IOException;

}
