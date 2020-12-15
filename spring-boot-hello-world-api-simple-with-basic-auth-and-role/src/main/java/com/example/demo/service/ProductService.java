package com.example.demo.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.example.demo.domain.Product;

public interface ProductService {

	String getProductDescription(String id);
	
	List<Product> getProductByName2(String regexp);
	
	List<Product> findProductByName2(String name);
	
	List<Product> getAllProducts();

	List<String> getAllProductNames();
	
	
	List<Product> getAllProducts_paged(Pageable pageable);
	
	List<Product> findProductByName_regex(String regexp);

	Product createProduct(Product newProduct);

	Product updateProduct(Product userUpdate);

	boolean deleteProduct(String id);

	Product getProductById(String id);

	List<Product> getProductByName(String name);
	
}