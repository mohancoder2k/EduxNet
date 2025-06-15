package com.smvec.ProfileService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smvec.ProfileService.Entity.StudentProfile;

import java.util.Optional;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    Optional<StudentProfile> findByUserEmail(String userEmail);
}
