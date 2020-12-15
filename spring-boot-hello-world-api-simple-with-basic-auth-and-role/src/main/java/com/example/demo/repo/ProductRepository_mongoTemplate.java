package com.example.demo.repo;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Product;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Transactional
@Repository()
public class ProductRepository_mongoTemplate {

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Product> findAllProducts(){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").exists(true));
		return mongoTemplate.find(query, Product.class);
	}


	public List<Product> findProductByName_regex(String regexp){
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(regexp));
		return mongoTemplate.find(query, Product.class);
	}

	public List<Product> findProductByName(String name){
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return mongoTemplate.find(query, Product.class);
	}

	public Product updateProduct(Product product){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(product.getId()));

		Update update = new Update();
		update.set("name", product.getName());
		update.set("price", product.getPrice());

		return mongoTemplate.findAndModify(query, update, Product.class);
	}

	public void deleteProductById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		mongoTemplate.remove(query, Product.class);
	}

	public List<Product> findAllProductsWithinPriceRange(){
		Query query = new Query();
		query.fields().exclude("id").exclude("name"); //exclude form from result
		query.addCriteria(Criteria.where("price").gt(18).lt(100));
		return mongoTemplate.find(query, Product.class);
	}


	public List<Product> findAllProductsWithinPriceRange2(){
		Query query = new Query();
		query.fields().include("id").include("name"); //include in result
		query.addCriteria(Criteria.where("price").gt(18).lt(100));
		return mongoTemplate.find(query, Product.class);
	}



	/*
	 * Aggregation with Mongotemplate (complex nested/chained operations all together)
	 * 
	 * MongoDB provides multiple native operators to perform aggregation $group,
	 * $order, $sort, etc. In spring data mongo, we can perform these operations
	 * with different aggregate functions. In Spring Data, we perform these
	 * aggregation with mainly 3 classes - Aggregation, AggregationOperation and
	 * AggregationResults.
	 * 
	 * https://docs.mongodb.com/manual/aggregation/
	 */
	public List<Product> findAllProducts_aggregation() {

		MatchOperation msgMatch = Aggregation.match(new Criteria("name").regex("John")); //$match

		GroupOperation groupByCity = Aggregation.group("address").sum("address").as("address"); //$group
		GroupOperation groupByCity_avg = Aggregation.group("address").avg("address").as("address");
		
		SortOperation priceSort = Aggregation.sort(Sort.Direction.DESC, "address"); //$sort

		AggregationOperation limit = Aggregation.limit(100);
		
		ProjectionOperation proj = project("address").and("name").previousOperation();
		
		Aggregation aggregation = Aggregation.newAggregation(msgMatch,  proj, priceSort, limit); //groupByCity, groupByCity_avg,

		return mongoTemplate.aggregate(aggregation, "user", Product.class).getMappedResults();
	}


	public List<Product> findAllUnValidatedProductsWithNames_aggregation(final List<String> names) {

		final Aggregation aggregation = newAggregation(
				match(Criteria
						.where("name").nin(names)
						.and("validated").ne(true)
						.and("price").in(Arrays.asList(100000d, 200000d))
						.and("description").nin(Arrays.asList("oil", "fruit"))
						)

				);

		AggregationResults<Product> aggregationResults = mongoTemplate.aggregate(aggregation, "user", Product.class);

		return aggregationResults.getMappedResults();  //.getUniqueMappedResult();
	}
}
