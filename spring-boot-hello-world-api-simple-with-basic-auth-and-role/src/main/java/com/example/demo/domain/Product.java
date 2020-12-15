package com.example.demo.domain;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document //@Document("products")
public class Product {
	
	@Id
	private String id;
	
	@NotNull
	private String name;
	
	@Min(0)
	private double price;
	private String description;
	private boolean Verified;
	
	private Date expiry;
	
	public Product() {
		super();
	}

	public String getId() {
		return id;
	}

	public Product setId(String id) {
		this.id = id;
		return  this;
	}

	public String getName() {
		return name;
	}

	public Product setName(String name) {
		this.name = name;
		return  this;
	}

	public double getPrice() {
		return price;
	}

	public Product setPrice(double price) {
		this.price = price;
		return  this;
	}

	public String getDescription() {
		return description;
	}

	public Product setDescription(String description) {
		this.description = description;
		return  this;
	}

	public boolean isVerified() {
		return Verified;
	}

	public Product setVerified(boolean verified) {
		Verified = verified;
		return  this;
	}

	public Date getExpiry() {
		return expiry;
	}

	public Product setExpiry(Date expiry) {
		this.expiry = expiry;
		return  this;
	}


}