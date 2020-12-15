package com.example.demo.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Product;

//@Transactional
@Repository
public interface ProductRepository extends MongoRepository<Product, String>{


	//--------------------------------------------------------------------
	
	Optional<Product> findById(String id);
	List<Product> findByName(String name);

	Page<Product> findById(String id, Pageable pageable);
	List<Product> findByName(String name, Sort sort);

	
	//--------------------------------------------------------------------
	
	Page<Product> findByIdContaining(String id, Pageable pageable);
	List<Product> findByNameContaining(String name, Sort sort);

	Page<Product> findByIdContainingIgnoreCase(String id, Pageable pageable);
	List<Product> findByNameContainingIgnoreCase(String name, Sort sort);

	Page<Product> findByIdLike(String id, Pageable pageable);
	List<Product> findByNameLike(String name, Sort sort);

	List<Product> findByNameStartingWith(String name);
	List<Product> findByNameEndingWith(String name);
	
	Page<Product> findByIdStartingWith(String id, Pageable pageable);
	List<Product> findByNameEndingWith(String name, Sort sort);

	Page<Product> findByIdLikeOrNameLike(String id, String name, Pageable pageable);
	List<Product> findByIdLikeAndNameLike(String id, String name, Sort sort);
	List<Product> findByNameLikeOrderByPriceAsc(String name);

	List<Product> findByPriceGreaterThan(double price);
	List<Product> findByPriceLessThan(double price);
	List<Product> findByPriceBetween(double from, double to);

	List<Product> findByVerifiedIsTrue();
	List<Product> findByVerifiedIsFalse();

	List<Product> findByExpiryDateAfter(Date date);
	List<Product> findByExpiryDateBefore(Date date);
	List<Product> findByExpiryDateBeforeOrderByPrice(Date date);
	
	boolean existsByName(String name);
	boolean existsByPrice(double price);
	
	//--------------------------------------------------------------------
	
	//@Query("SELECT u FROM Product u WHERE u.name = :name") //SQL QUERY
	@Query("{ 'name' : ?0}") //NOSQL QUERY
	List<Product> getProductByName(@Param("name") String name);

	@Query(value = "{ 'name' : ?0}", sort = "{price: -1}" ) //NOSQL QUERY
	List<Product> getProductByName_sort(@Param("name") String name);

	@Query("{ 'name' : ?0}") //NOSQL QUERY
	List<Product> getProductByName_page(@Param("name") String name);

	@Query("{ 'name' : ?0}, { 'price' : ?1}") //NOSQL QUERY
	List<Product> getProductByNameAndPrice_page(@Param("name") String name, @Param("price") double price);

	@Query("{ 'name' : ?0}, { 'friends.name' : ?1}") //NOSQL QUERY
	List<Product> getProductByNameAndPrice_otherNestedFields(@Param("name") String name, @Param("field") String field);

	@Query("{ 'name' : {$regex: '^?0'}}") //NOSQL QUERY
	List<Product> getProductByName_regex(@Param("name") String name);

	@Query("{ 'name' : { $regex: ?0 } }") //NOSQL QUERY
	List<Product> findProductsByName_Regex(String regex);
	
	@Query("{ 'price' : { $gt: ?0, $lt: ?1 } }")
	List<Product> findProductsByPriceBetween(double startPrice, double endPrice);
	
	//--------------------------------------------------------------------
	
	default List<Product> findAll_paged(Pageable pageable) {
		return StreamSupport.stream(findAll(pageable).spliterator(), false).collect(Collectors.toList());
	}

	default List<Product> getAllProducts(){
		return   StreamSupport.stream(findAll().spliterator(), false).collect(Collectors.toList());
	}
	
	
	//--------------------------------------------------------------------
	



}
