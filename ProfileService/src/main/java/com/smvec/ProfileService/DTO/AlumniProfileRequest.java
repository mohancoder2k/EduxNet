package com.smvec.ProfileService.DTO;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumniProfileRequest {
    private String name;
    private String department;
    private int graduationYear;
    private String profession;
    private String linkedin;
}
