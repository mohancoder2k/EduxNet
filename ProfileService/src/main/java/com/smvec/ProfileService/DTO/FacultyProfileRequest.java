package com.smvec.ProfileService.DTO;



import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultyProfileRequest {
    private String name;
    private String department;
    private String designation;
    private String researchInterests;
}
