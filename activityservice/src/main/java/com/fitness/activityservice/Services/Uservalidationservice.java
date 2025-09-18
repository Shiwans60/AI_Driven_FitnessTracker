package com.fitness.activityservice.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class Uservalidationservice {
    private final WebClient userservicewebclient;
    public Boolean validateuser(String userId){
        try{
            return userservicewebclient.get()
                    .uri("/Users/getuser/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        }
        catch (WebClientResponseException e){
            if(e.getStatusCode()== HttpStatus.NOT_FOUND){
                throw new RuntimeException("User not found" + userId);
            } else if (e.getStatusCode()== HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Invalid user id " + userId);
            }
            return false;
        }
    }
}
