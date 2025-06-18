package com.smvec.ConnectionService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smvec.ConnectionService.DTO.ConnectionRequestDTO;
import com.smvec.ConnectionService.Entity.ConnectionRequest;
import com.smvec.ConnectionService.Service.ConnectionRequestService;

import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/chat/connection")
public class ConnectionRequestController {

    @Autowired
    private ConnectionRequestService connectionService;

    /**
     * Send a connection request to another user
     */
    @PostMapping("/send")
    public Mono<ResponseEntity<String>> sendConnectionRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody ConnectionRequestDTO requestDTO) {

        return connectionService.sendRequest(token, requestDTO)
                .map(message -> ResponseEntity.ok(message))
                .defaultIfEmpty(ResponseEntity.badRequest().body("❌ Unable to process request"));
    }

    /**
     * Respond to a connection request (accept/reject)
     */
    @PostMapping("/respond")
    public Mono<ResponseEntity<String>> respondToRequest(
            @RequestHeader("Authorization") String token,
            @RequestParam(name = "fromUsername") String fromUsername,
            @RequestParam(name = "response") String response) {

        return connectionService.respondToRequest(token, fromUsername, response)
                .map(ResponseEntity::ok);
    }

    /**
     * Get all pending connection requests for logged-in user
     */
    @GetMapping("/pending")
    public Mono<ResponseEntity<List<ConnectionRequest>>> getPendingRequests(
            @RequestHeader("Authorization") String token) {

        return connectionService.getPendingRequests(token)
                .map(ResponseEntity::ok);
    }
    @GetMapping("/status")
    public Mono<ResponseEntity<String>> getConnectionStatus(
            @RequestHeader("Authorization") String token,
            @RequestParam(name = "viewedUserEmail") String viewedUserEmail) {
        return connectionService.getConnectionStatus(token, viewedUserEmail)
                .map(ResponseEntity::ok);
    }




}
