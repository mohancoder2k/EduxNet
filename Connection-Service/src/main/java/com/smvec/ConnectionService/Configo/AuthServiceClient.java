package com.smvec.ConnectionService.Configo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import java.util.Map;

@Component
public class AuthServiceClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<String> getUsernameFromToken(String token) {
        return webClientBuilder.build()
            .get()
            .uri("http://AUTH-SERVICE/auth/check-token")
            .header("Authorization", token)
            .retrieve()
            .bodyToMono(Map.class)
            .map(response -> (String) response.get("username"));
    }
}
