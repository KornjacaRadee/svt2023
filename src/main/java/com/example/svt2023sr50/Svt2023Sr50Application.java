package com.example.svt2023sr50;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Svt2023Sr50Application {

	public static void main(String[] args) {
		SpringApplication.run(Svt2023Sr50Application.class, args);
	}

}
