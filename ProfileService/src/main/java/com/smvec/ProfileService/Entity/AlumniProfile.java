package com.smvec.ProfileService.Entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alumni_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumniProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    private String name;
    private String department;
    private int graduationYear;
    private String profession;
    private String linkedin;
}
