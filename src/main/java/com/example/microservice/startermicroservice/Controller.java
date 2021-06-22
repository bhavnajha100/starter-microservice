package com.example.microservice.startermicroservice;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.*;


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
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        String url="http://localhost:8080/counter";
        return restTemplate.getForObject(url, Integer.class);
        //return sendData(response+1);
    }

   @GetMapping("/incrementConcurrently")
    public int incrementConcurrently() throws JSONException {
        int response = 0;
        for(int i=0; i<5; i++){
           response =  getData();
        }
        return response;
    }

    /**
     * This method gets the updated counter from the db and decrement it.
     *
     * @return Decremented counter
     * @throws JSONException
     */
    @GetMapping("/decrement")
    public int decrementCounter() throws JSONException {
       // decrement();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        String url="http://localhost:8080/decrementCounter";
        return restTemplate.getForObject(url, Integer.class);

    }


    @GetMapping("/decrementConcurrently")
    public int decrementConcurrently() throws JSONException {
        int response = 0;
        for(int i=0; i<10; i++){
            response = getDeleteData();
        }
        return response;
    }



    public Integer getData(){
        ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
        Future<Integer> future = executor.submit(new Task(restTemplate));
        Integer response = null;

        try {
            response = future.get(1000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }

    public Integer getDeleteData(){
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Future<Integer> future = executor.submit(new DecrementTask(restTemplate));
        Integer response = null;

        try {
            response = future.get(1000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }
}
