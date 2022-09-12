package com.andraskrausz.restfulapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestfulApp {

	public static void main(String[] args) {
		SpringApplication.run(RestfulApp.class, args);
	}

}
