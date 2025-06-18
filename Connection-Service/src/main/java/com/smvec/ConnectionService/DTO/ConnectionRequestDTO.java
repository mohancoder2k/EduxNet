package com.smvec.ConnectionService.DTO;



public class ConnectionRequestDTO {

    private String receiverUsername;

    // Default constructor
    public ConnectionRequestDTO() {}

    // Constructor
    public ConnectionRequestDTO(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    // Getter and Setter
    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }
}
