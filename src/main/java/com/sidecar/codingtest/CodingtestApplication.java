package com.sidecar.codingtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CodingtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingtestApplication.class, args);
	}

}
