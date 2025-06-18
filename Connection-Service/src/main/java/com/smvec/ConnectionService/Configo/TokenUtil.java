package com.smvec.ConnectionService.Configo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TokenUtil {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<String> extractEmailFromToken(String token) {
        return webClientBuilder.build()
                .get()
                .uri("http://AUTH-SERVICE/auth/check-token")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .flatMap(resp -> {
                    if (resp.isValid()) return Mono.just(resp.getEmail());
                    else return Mono.error(new RuntimeException("Invalid token"));
                });
    }
    
}
