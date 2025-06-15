package com.smvec.ProfileService.DTO;



import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileRequest {
    private String name;
    private String department;
    private int year;
    private String skills;
    private String github;
}
