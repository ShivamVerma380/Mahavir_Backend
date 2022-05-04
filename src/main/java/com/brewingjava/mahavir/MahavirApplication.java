package com.brewingjava.mahavir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MahavirApplication {

	public static void main(String[] args) {
		SpringApplication.run(MahavirApplication.class, args);
		
	}

}
