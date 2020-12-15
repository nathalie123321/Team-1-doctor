package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;

@RequestMapping(value = "/api/v1/products")
@RestController
@Validated
public class ProductsController_validated {

	@Autowired
	private ProductService productService;

	
	/*
	 
	 .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .-----------------.
	| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |
	| |   _____      | || |     ____     | || |    ______    | || |    ______    | || |  _________   | || |  ________    | || |              | || |     _____    | || | ____  _____  | |
	| |  |_   _|     | || |   .'    `.   | || |  .' ___  |   | || |  .' ___  |   | || | |_   ___  |  | || | |_   ___ `.  | || |              | || |    |_   _|   | || ||_   \|_   _| | |
	| |    | |       | || |  /  .--.  \  | || | / .'   \_|   | || | / .'   \_|   | || |   | |_  \_|  | || |   | |   `. \ | || |    ______    | || |      | |     | || |  |   \ | |   | |
	| |    | |   _   | || |  | |    | |  | || | | |    ____  | || | | |    ____  | || |   |  _|  _   | || |   | |    | | | || |   |______|   | || |      | |     | || |  | |\ \| |   | |
	| |   _| |__/ |  | || |  \  `--'  /  | || | \ `.___]  _| | || | \ `.___]  _| | || |  _| |___/ |  | || |  _| |___.' / | || |              | || |     _| |_    | || | _| |_\   |_  | |
	| |  |________|  | || |   `.____.'   | || |  `._____.'   | || |  `._____.'   | || | |_________|  | || | |________.'  | || |              | || |    |_____|   | || ||_____|\____| | |
	| |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | |
	| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |
	 '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------' 

	 */
	
	@PreAuthorize("hasAuthority('USER') OR hasAuthority('ADMIN')")
	@GetMapping(value = "/")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}
	
	@PreAuthorize("hasAuthority('USER') OR hasAuthority('ADMIN')")
	@GetMapping(value = "/getProductByName2")
	public List<Product> getAllProducts2(@Valid @Size(min = 1, max = 50) @RequestParam String regexp) {
		return productService.getProductByName2(regexp);
	}

	@PreAuthorize("hasAuthority('USER') OR hasAuthority('ADMIN')")
	@GetMapping(value = "/findProductByName2")
	private List<Product> findProductByName2(@Valid @Size(min = 1, max = 50) @RequestParam String name) {
		return productService.findProductByName2(name);
	}

	@PreAuthorize("hasAuthority('USER') OR hasAuthority('ADMIN')")
	@RequestMapping(value = "/getProductById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product getProductById(@Valid @Size(min = 1, max = 50) @RequestParam String id) {
		return productService.getProductById(id);
	}

	@PreAuthorize("hasAuthority('USER') OR hasAuthority('ADMIN')")
	@RequestMapping(value = "/getProductById2/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product getProductById2(@Valid @Size(min = 1, max = 50) @PathVariable("id") String id) {
		return productService.getProductById(id);
	}

	@PreAuthorize("hasAuthority('USER') OR hasAuthority('ADMIN')")
	@RequestMapping(value = "/getProductById3/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product getProductById3(@Valid @Size(min = 1, max = 50) @PathVariable("id") String id, HttpServletRequest req) {
		req.getHeaderNames().asIterator().forEachRemaining(System.out::println);
		req.getHeaders("user-agent").asIterator().forEachRemaining(System.out::println);
		return productService.getProductById(id);
	}

	
	@PreAuthorize("hasAuthority('USER') OR hasAuthority('ADMIN')")
	@GetMapping("/getAllProducts_paged")
	public ResponseEntity<Map<String, Object>> getAllProducts_paged(@RequestParam(required = false) String title,
			@Valid @Min(0) @RequestParam(defaultValue = "0") int page, @Valid @Min(0) @Max(value = 100, message = "Please provide value within range of 0 to 100") @RequestParam(defaultValue = "3") int size) {

		Pageable pageable = PageRequest.of(page, size);

		//CONVERT LIST TO PAGE
		Page<Product> result = new PageImpl<>(productService.getAllProducts_paged(pageable));

		Map<String, Object> response = new HashMap<>();
		response.put("result", result);
		response.put("currentPage", result.getNumber());
		response.put("totalItems", result.getTotalElements());
		response.put("totalPages", result.getTotalPages());

		return new ResponseEntity<>(response, HttpStatus.OK);

	}


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
	@PostMapping(value = "/createProduct")
	public Product createProduct(@Valid @RequestBody Product newProduct) {
		return productService.createProduct(newProduct);
	}


	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "/deleteProduct")
	public boolean deleteProduct(@Valid @Size(min = 1, max = 50) @RequestParam String id) {
		return productService.deleteProduct(id);
	}


}



