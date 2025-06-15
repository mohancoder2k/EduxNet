package com.smvec.ProfileService.Service;

import com.smvec.ProfileService.DTO.*;
import com.smvec.ProfileService.Entity.*;
import com.smvec.ProfileService.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final StudentProfileRepository studentRepo;
    private final AlumniProfileRepository alumniRepo;
    private final FacultyProfileRepository facultyRepo;
    private final WebClient.Builder webClientBuilder;

    private String extractEmailFromToken(String token) {
        return webClientBuilder.build()
                .get()
                .uri("http://AUTH-SERVICE/auth/check-token")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .map(resp -> {
                    if (resp.isValid()) return resp.getEmail();
                    else throw new RuntimeException("Invalid token");
                })
                .block();
    }

    // STUDENT

    @Override
    public StudentProfileResponse saveStudentProfile(String token, StudentProfileRequest request) {
        String email = extractEmailFromToken(token);
        if (studentRepo.findByUserEmail(email).isPresent()) {
            throw new IllegalStateException("Student profile already exists");
        }

        StudentProfile profile = StudentProfile.builder()
                .userEmail(email)
                .name(request.getName())
                .department(request.getDepartment())
                .year(request.getYear())
                .skills(request.getSkills())
                .github(request.getGithub())
                .build();

        studentRepo.save(profile);
        return mapStudent(profile);
    }

    @Override
    public StudentProfileResponse updateStudentProfile(String token, StudentProfileRequest request) {
        String email = extractEmailFromToken(token);
        StudentProfile profile = studentRepo.findByUserEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Student profile not found"));

        profile.setName(request.getName());
        profile.setDepartment(request.getDepartment());
        profile.setYear(request.getYear());
        profile.setSkills(request.getSkills());
        profile.setGithub(request.getGithub());

        studentRepo.save(profile);
        return mapStudent(profile);
    }

    @Override
    public StudentProfileResponse getStudentProfile(String token) {
        String email = extractEmailFromToken(token);
        StudentProfile profile = studentRepo.findByUserEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Student profile not found"));
        return mapStudent(profile);
    }

    private StudentProfileResponse mapStudent(StudentProfile profile) {
        return StudentProfileResponse.builder()
                .userEmail(profile.getUserEmail())
                .name(profile.getName())
                .department(profile.getDepartment())
                .year(profile.getYear())
                .skills(profile.getSkills())
                .github(profile.getGithub())
                .build();
    }

    // ALUMNI

    @Override
    public AlumniProfileResponse saveAlumniProfile(String token, AlumniProfileRequest request) {
        String email = extractEmailFromToken(token);
        if (alumniRepo.findByUserEmail(email).isPresent()) {
            throw new IllegalStateException("Alumni profile already exists");
        }

        AlumniProfile profile = AlumniProfile.builder()
                .userEmail(email)
                .name(request.getName())
                .department(request.getDepartment())
                .graduationYear(request.getGraduationYear())
                .profession(request.getProfession())
                .linkedin(request.getLinkedin())
                .build();

        alumniRepo.save(profile);
        return mapAlumni(profile);
    }

    @Override
    public AlumniProfileResponse updateAlumniProfile(String token, AlumniProfileRequest request) {
        String email = extractEmailFromToken(token);
        AlumniProfile profile = alumniRepo.findByUserEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Alumni profile not found"));

        profile.setName(request.getName());
        profile.setDepartment(request.getDepartment());
        profile.setGraduationYear(request.getGraduationYear());
        profile.setProfession(request.getProfession());
        profile.setLinkedin(request.getLinkedin());

        alumniRepo.save(profile);
        return mapAlumni(profile);
    }

    @Override
    public AlumniProfileResponse getAlumniProfile(String token) {
        String email = extractEmailFromToken(token);
        AlumniProfile profile = alumniRepo.findByUserEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Alumni profile not found"));
        return mapAlumni(profile);
    }

    private AlumniProfileResponse mapAlumni(AlumniProfile profile) {
        return AlumniProfileResponse.builder()
                .userEmail(profile.getUserEmail())
                .name(profile.getName())
                .department(profile.getDepartment())
                .graduationYear(profile.getGraduationYear())
                .profession(profile.getProfession())
                .linkedin(profile.getLinkedin())
                .build();
    }

    // FACULTY

    @Override
    public FacultyProfileResponse saveFacultyProfile(String token, FacultyProfileRequest request) {
        String email = extractEmailFromToken(token);
        if (facultyRepo.findByUserEmail(email).isPresent()) {
            throw new IllegalStateException("Faculty profile already exists");
        }

        FacultyProfile profile = FacultyProfile.builder()
                .userEmail(email)
                .name(request.getName())
                .department(request.getDepartment())
                .designation(request.getDesignation())
                .researchInterests(request.getResearchInterests())
                .build();

        facultyRepo.save(profile);
        return mapFaculty(profile);
    }

    @Override
    public FacultyProfileResponse updateFacultyProfile(String token, FacultyProfileRequest request) {
        String email = extractEmailFromToken(token);
        FacultyProfile profile = facultyRepo.findByUserEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Faculty profile not found"));

        profile.setName(request.getName());
        profile.setDepartment(request.getDepartment());
        profile.setDesignation(request.getDesignation());
        profile.setResearchInterests(request.getResearchInterests());

        facultyRepo.save(profile);
        return mapFaculty(profile);
    }

    @Override
    public FacultyProfileResponse getFacultyProfile(String token) {
        String email = extractEmailFromToken(token);
        FacultyProfile profile = facultyRepo.findByUserEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Faculty profile not found"));
        return mapFaculty(profile);
    }

    private FacultyProfileResponse mapFaculty(FacultyProfile profile) {
        return FacultyProfileResponse.builder()
                .userEmail(profile.getUserEmail())
                .name(profile.getName())
                .department(profile.getDepartment())
                .designation(profile.getDesignation())
                .researchInterests(profile.getResearchInterests())
                .build();
    }
}
