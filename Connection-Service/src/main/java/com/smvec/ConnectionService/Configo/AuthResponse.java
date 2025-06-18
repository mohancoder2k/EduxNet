package com.smvec.ConnectionService.Configo;




import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String email;
    private boolean valid;
}