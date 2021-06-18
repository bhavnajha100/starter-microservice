package com.example.microservice.startermicroservice;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.microservice.startermicroservice.Controller.Counter.*;

@RestController
public class Controller {


    @Autowired
    RestTemplate restTemplate;

    /**
     * This method gets the updated counter from the db and increment it.
     *
     * @return Incremented counter
     * @throws JSONException
     */
    @GetMapping("/increment")
    public int incrementCounter() throws JSONException {
        increment();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        String url="http://localhost:8080/counter";
        Integer response = restTemplate.getForObject(url, Integer.class);
        return sendData(response+1);
    }

    /**
     * This method gets the updated counter from the db and decrement it.
     *
     * @return Decremented counter
     * @throws JSONException
     */
    @GetMapping("/decrement")
    public int decrementCounter() throws JSONException {
        decrement();
        String url="http://localhost:8080/counter";
        Integer response = restTemplate.getForObject(url, Integer.class);

       return sendData(response-1);
    }

    /**
     * This method sets the updated value of counter in db.
     * @param updatedValue :
     * @return
     * @throws JSONException
     */
    private Integer sendData(int updatedValue) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url="http://localhost:8080/counter";
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",updatedValue);
        HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);
        return restTemplate.
                postForObject(url, request, Integer.class);

    }

    /*
        Counter class to increment and decrement the counter atomically.
     */
    public static class Counter {

        private static AtomicInteger atomicInteger = new AtomicInteger(0);

        public static void increment() {
            atomicInteger.getAndIncrement();
        }

        public static void decrement() {
            atomicInteger.getAndDecrement();
        }

        public int value() {
            return atomicInteger.get();
        }


    }
}
