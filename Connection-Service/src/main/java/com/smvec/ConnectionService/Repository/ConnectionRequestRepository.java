package com.smvec.ConnectionService.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smvec.ConnectionService.Entity.ConnectionRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    Optional<ConnectionRequest> findBySenderUsernameAndReceiverUsername(String sender, String receiver);

    List<ConnectionRequest> findByReceiverUsernameAndStatus(String receiver, ConnectionRequest.Status status);

    List<ConnectionRequest> findBySenderUsernameOrReceiverUsername(String sender, String receiver);
}
