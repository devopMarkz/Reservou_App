package com.github.devopMarkz.api_reservou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiReservouApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiReservouApplication.class, args);
	}

}
