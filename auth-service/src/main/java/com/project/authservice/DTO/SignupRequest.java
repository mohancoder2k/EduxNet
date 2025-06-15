package com.project.authservice.DTO;


import com.project.authservice.Entity.Role;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private Role role; // STUDENT / ALUMNI / FACULTY
}
