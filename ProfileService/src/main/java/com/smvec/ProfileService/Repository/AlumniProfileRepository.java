package com.smvec.ProfileService.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.smvec.ProfileService.Entity.AlumniProfile;

import java.util.Optional;

public interface AlumniProfileRepository extends JpaRepository<AlumniProfile, Long> {
    Optional<AlumniProfile> findByUserEmail(String userEmail);
}
