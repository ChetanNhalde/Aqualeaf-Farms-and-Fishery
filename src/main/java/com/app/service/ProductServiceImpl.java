package com.app.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.ProductDao;
import com.app.dto.ApiResponse;
import com.app.dto.ProductDTO;
import com.app.entities.Product;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDao productDao;


	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private FileService fileService;
	
	@Value("${file.upload.location}")
	private String path;
	
	@Override
	public ProductDTO addProduct(Product product) {
		
		product.setImagePath("default.png");
		Product savedProduct = productDao.save(product);
		return mapper.map(savedProduct, ProductDTO.class);
		
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		List<Product> products = productDao.findAll();
		return products.
				stream()
				.map(product -> mapper.map(product, ProductDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public ProductDTO updateProduct(Long id, Product product) {
		Product foundProduct = productDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid Product Id!!!"));
		mapper.map(product, foundProduct);
		ProductDTO productDTO = mapper.map(foundProduct, ProductDTO.class);
		productDTO.setId(id);
		return productDTO;
	}

	@Override
	public ApiResponse deleteProduct(Long id) {
		Product product = productDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid Product Id!!!"));
		productDao.delete(product);
		return new ApiResponse("Product Details of product with ID " + product.getId() + " deleted....");
	}

	@Override
	public ProductDTO getProduct(Long id) {
		Product product = productDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid Product Id!!!"));
		return mapper.map(product, ProductDTO.class);
	}

	@Override
	public ProductDTO updateProductImage(Long id, MultipartFile image) throws IOException {
		Product product = productDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid Product Id!!!"));
		String fileName = fileService.uploadImage(path, image);
		
		product.setImagePath(fileName);
		
		Product updatedProduct = productDao.save(product);
		
		return mapper.map(updatedProduct, ProductDTO.class);
		
	}
	
	
	
	
	
	
	

}
