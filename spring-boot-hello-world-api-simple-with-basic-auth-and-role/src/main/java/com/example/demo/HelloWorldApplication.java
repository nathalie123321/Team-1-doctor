package com.example.demo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.demo.domain.Product;
import com.example.demo.repo.ProductRepository;
import com.example.demo.repo.ProductRepository_mongoTemplate;
import com.github.lalyos.jfiglet.FigletFont;

@EnableScheduling
@SpringBootApplication
public class HelloWorldApplication {

	private static final Logger log = LoggerFactory.getLogger(HelloWorldApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
		log.info("Hello World!");
		AsciiArtCreator.getAsciiArt("only admin");
	}

	@Bean
	public CommandLineRunner demo(ProductRepository repository, ProductRepository_mongoTemplate mtemplate) {

		return (args) -> {

			log.info("bean was executed!");

			// DELETE ALL
			repository.deleteAll();

			// SAVE USER
			Product user1 = new Product().setName("Manja juice").setPrice(50.00d).setDescription("very nice")
					.setVerified(true).setExpiry(new Date());

			repository.save(user1);
			repository.save(new Product().setName("Manja juice").setPrice(12.90d).setDescription("very nice")
					.setVerified(true).setExpiry(new Date()));
			repository.save(new Product().setName("Banana juice").setPrice(20.00d).setDescription("very nice")
					.setVerified(true).setExpiry(new Date()));
			repository.save(new Product().setName("Apple juice").setPrice(15.00d).setDescription("very nice")
					.setVerified(true).setExpiry(new Date()));
			repository.save(new Product().setName("grape juice").setPrice(14.00d).setDescription("very nice")
					.setVerified(true).setExpiry(new Date()));

			// GET ALL USER
			int counter = 0;
			for (Product product : repository.findAll()) {
				++counter;
				log.info(counter + ". " + product);
			}

			// FIND USER BY ID
			try {
				Product product = repository.findById(user1.getId()).get();
				log.info("user: {}", product);

				List<Product> products = mtemplate.findAllProducts_aggregation();
				log.info("user: {}", products);

				List<Product> users2 = mtemplate.findAllUnValidatedProductsWithNames_aggregation(Arrays.asList("John"));
				log.info("user: {}", users2);

			} catch (Exception e) {
				e.printStackTrace();
			}

		};
	}
}

class AsciiArtCreator {

	public static String getAsciiArt(String text){
		try {
			FigletFont.convertOneLine(text);
		}
		catch ( IOException e ) {
			e.printStackTrace();
			return "Could not get ASCII art";
		}
		return text;
	}

}