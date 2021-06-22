package com.example.microservice.startermicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.Callable;

public class Task implements Callable<Integer> {

    private RestTemplate restTemplate;

    public Task(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Integer call() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        String url="http://localhost:8080/counter";
       return  restTemplate.getForObject(url, Integer.class);
    }
}
