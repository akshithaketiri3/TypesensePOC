package com.example.typesense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.typesense"})
public class TypesenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(TypesenseApplication.class, args);
	}

}
