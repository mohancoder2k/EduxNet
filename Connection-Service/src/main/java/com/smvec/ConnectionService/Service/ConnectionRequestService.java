package com.smvec.ConnectionService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smvec.ConnectionService.Configo.AuthServiceClient;
import com.smvec.ConnectionService.Configo.TokenUtil;
import com.smvec.ConnectionService.DTO.ConnectionRequestDTO;
import com.smvec.ConnectionService.Entity.ConnectionRequest;
import com.smvec.ConnectionService.Entity.ConnectionRequest.Status;
import com.smvec.ConnectionService.Repository.ConnectionRequestRepository;

import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ConnectionRequestService {

    @Autowired
    private ConnectionRequestRepository repository;

    @Autowired
    private AuthServiceClient authServiceClient;

    /**
     * Send a connection request
     */
    @Autowired
    private TokenUtil tokenUtil;

    public Mono<String> sendRequest(String token, ConnectionRequestDTO dto) {
        return tokenUtil.extractEmailFromToken(token)
            .flatMap(senderEmail -> {
                if (senderEmail.equals(dto.getReceiverUsername())) {
                    return Mono.just("❌ Cannot send request to yourself");
                }

                boolean alreadyExists = repository.findBySenderUsernameAndReceiverUsername(senderEmail, dto.getReceiverUsername()).isPresent()
                    || repository.findBySenderUsernameAndReceiverUsername(dto.getReceiverUsername(), senderEmail).isPresent();

                if (alreadyExists) {
                    return Mono.just("⚠️ Request already exists or already connected");
                }

                ConnectionRequest request = new ConnectionRequest();
                request.setSenderUsername(senderEmail);
                request.setReceiverUsername(dto.getReceiverUsername());
                request.setStatus(Status.PENDING);
                repository.save(request);
                return Mono.just("✅ Request sent successfully");
            });
    }



    /**
     * Accept or reject a connection request
     */
    public Mono<String> respondToRequest(String token, String fromUsername, String response) {
        return tokenUtil.extractEmailFromToken(token)
            .flatMap(currentEmail -> {
                ConnectionRequest request = repository
                        .findBySenderUsernameAndReceiverUsername(fromUsername, currentEmail)
                        .orElse(null);

                if (request == null) {
                    return Mono.just("❌ Request not found");
                }

                if ("ACCEPTED".equalsIgnoreCase(response)) {
                    request.setStatus(Status.ACCEPTED);
                    repository.save(request);
                    return Mono.just("✅ Request accepted");
                } else if ("REJECTED".equalsIgnoreCase(response)) {
                    repository.delete(request);
                    return Mono.just("❌ Request rejected");
                }

                return Mono.just("⚠️ Invalid response");
            });
    }


    /**
     * Get all pending requests for current user
     */
    public Mono<List<ConnectionRequest>> getPendingRequests(String token) {
        return tokenUtil.extractEmailFromToken(token)
                .flatMap(email -> Mono.just(repository.findByReceiverUsernameAndStatus(email, Status.PENDING)));
    }

    /**
     * Check if two users are connected
     */
    public boolean areConnected(String user1, String user2) {
        return repository.findBySenderUsernameAndReceiverUsername(user1, user2)
                .map(r -> r.getStatus() == Status.ACCEPTED)
                .orElse(false);
    }
    public Mono<String> getConnectionStatus(String token, String viewedUserEmail) {
        return tokenUtil.extractEmailFromToken(token)
                .map(currentUserEmail -> {
                    boolean isConnected = repository
                            .findBySenderUsernameAndReceiverUsername(currentUserEmail, viewedUserEmail)
                            .map(r -> r.getStatus() == Status.ACCEPTED)
                            .orElseGet(() ->
                                    repository.findBySenderUsernameAndReceiverUsername(viewedUserEmail, currentUserEmail)
                                            .map(r -> r.getStatus() == Status.ACCEPTED)
                                            .orElse(false)
                            );

                    return isConnected ? "CONNECTED" : "NOT_CONNECTED";
                });
    }

}
