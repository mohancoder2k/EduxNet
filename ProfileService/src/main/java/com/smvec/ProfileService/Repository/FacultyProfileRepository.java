package com.smvec.ProfileService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smvec.ProfileService.Entity.FacultyProfile;

import java.util.Optional;

public interface FacultyProfileRepository extends JpaRepository<FacultyProfile, Long> {
    Optional<FacultyProfile> findByUserEmail(String userEmail);
}
