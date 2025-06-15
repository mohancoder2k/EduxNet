package com.smvec.ProfileService.DTO;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileResponse {
    private String name;
    private String department;
    private int year;
    private String skills;
    private String github;
    private String userEmail;
}
