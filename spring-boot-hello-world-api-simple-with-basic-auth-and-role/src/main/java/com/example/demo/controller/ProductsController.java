package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;

@RequestMapping(value = "/api/v1/products")
@RestController
public class ProductsController {

	@Autowired
	private ProductService productService;

	


	/*
	.----------------.  .----------------.  .----------------.  .----------------.  .-----------------.
	| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |
	| |      __      | || |  ________    | || | ____    ____ | || |     _____    | || | ____  _____  | |
	| |     /  \     | || | |_   ___ `.  | || ||_   \  /   _|| || |    |_   _|   | || ||_   \|_   _| | |
	| |    / /\ \    | || |   | |   `. \ | || |  |   \/   |  | || |      | |     | || |  |   \ | |   | |
	| |   / ____ \   | || |   | |    | | | || |  | |\  /| |  | || |      | |     | || |  | |\ \| |   | |
	| | _/ /    \ \_ | || |  _| |___.' / | || | _| |_\/_| |_ | || |     _| |_    | || | _| |_\   |_  | |
	| ||____|  |____|| || | |________.'  | || ||_____||_____|| || |    |_____|   | || ||_____|\____| | |
	| |              | || |              | || |              | || |              | || |              | |
	| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |
	'----------------'  '----------------'  '----------------'  '----------------'  '----------------' 
	*/
	

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "/updateProduct")
	public Product updateProduct(@RequestBody Product userUpdate) {
		return productService.updateProduct(userUpdate);
	}



}



