package com.example.microservice.startermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@SpringBootApplication
public class StarterMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarterMicroserviceApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

}
