package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Product;
import com.example.demo.repo.ProductRepository;
import com.example.demo.repo.ProductRepository_mongoTemplate;

@Service
public class ProductServiceImpl implements ProductService {
	
	
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);


	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductRepository_mongoTemplate productMongoTemplate;


	@Override
	public String getProductDescription(String id) {
		return productRepository.findById(id).get().getDescription();
	}

	@Override
	public List<Product> getProductByName(String name) {
		return productRepository.findByName(name);
	}
	
	
	@Override
	public List<Product> getProductByName2(String regexp) {
		return productMongoTemplate.findProductByName_regex(regexp);
	}

	@Override
	public List<Product> findProductByName2(String name) {
		return productMongoTemplate.findProductByName(name);
	}
	
	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<String> getAllProductNames() {
		return productRepository.findAll().stream().map(Product::getName).collect(Collectors.toList());
	}

	
	@Override
	public List<Product> getAllProducts_paged(Pageable pageable) {
		return productRepository.findAll_paged(pageable).stream().collect(Collectors.toList());
	}
	

	@Override
	public List<Product> findProductByName_regex(String regexp) {
		return productMongoTemplate.findProductByName_regex(regexp);
	}

	@Override
	public Product createProduct(Product newProduct) {
		Product product = new Product().setName("Manja juice").setPrice(10.00d).setDescription("very nice").setVerified(true).setExpiry(new  Date());

		productRepository.insert(product);
		return product;
	}

	@Override
	public Product updateProduct(Product productUpdate) {
		if(productRepository.existsById(productUpdate.getId())) {
			productRepository.save(productUpdate);
		}else {
			
			log.warn("Sorry product with the particular id doesnt exist!");
			return productUpdate;
			
		}
		return productRepository.findById(productUpdate.getId()).get();
	}

	@Override
	public boolean deleteProduct(String id) {
		
		if (productRepository.existsById(id)) {
			
			try {
				productRepository.deleteById(id);
				
				return true;
			} catch (Exception e) {
				log.error(e.getMessage());
			}

		}
		
		return false;
	}

	@Override
	public Product getProductById(String id) {
		
		if (productRepository.existsById(id)) {
			return productRepository.findById(id).get();
		}
		
		return null;
	}


	@Scheduled(initialDelay = 1000 * 60,fixedRate=1000 * 60)
	private void checkIfProductIsExpired() {

		System.out.println("checking if any product is expired...");

	}


}
