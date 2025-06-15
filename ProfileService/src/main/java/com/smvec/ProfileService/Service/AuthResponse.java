package com.smvec.ProfileService.Service;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String email;
    private boolean valid;
}
