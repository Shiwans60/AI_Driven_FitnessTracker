package com.server.aiservice.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeminiService {
    private final WebClient webClient;
    @Value("${gemini.api.key}")
    private String GeminiApiKey;
    @Value("${gemini.api.url}")
    private String GeminiApiUrl;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();

    }
    public String GetAnswer(String Questions){
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", Questions),
                        })
                }

        );
        String repsonse = webClient.post()
                .uri(GeminiApiUrl)
                .header("Content-Type", "application/json")
                .header("X-goog-api-key", GeminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return repsonse;


    }
}
