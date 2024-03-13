package com.demoProject.crp.Service;

import com.demoProject.crp.Dto.*;
import com.demoProject.crp.Entity.Admin;
import com.demoProject.crp.Entity.Customer;
import com.demoProject.crp.Entity.Role;
import com.demoProject.crp.Mapper.AdminMapper;
import com.demoProject.crp.Mapper.CustomerMapper;
import com.demoProject.crp.Repository.AdminRepository;
import com.demoProject.crp.SecurityConfig.Util.JwtUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AdminService {
    @Autowired

    private  BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  AdminRepository adminRepository;
    @Autowired
    private  JwtUtils jwtUtils;

        @PostConstruct
        public void initData() {
            // Check if any admins exist in the database
            if (adminRepository.count() == 0) {
                // If no admins found, create a default admin
                Admin defaultAdmin = new Admin();
                defaultAdmin.setId(1);
                defaultAdmin.setName("Ayushi");
                defaultAdmin.setUsername("ayushi83");
                defaultAdmin.setEmail("ayushi83@gmail.com");
                defaultAdmin.setPassword(passwordEncoder.encode("ayushi123")); // Use proper password encoding
                defaultAdmin.setRole(Role.ADMIN);
                // Save the default admin to the database
                adminRepository.save(defaultAdmin);
            }
        }

    public ResponseEntity<LoginResponse> signInAdmin(LoginRequest loginRequest) {

        LoginResponse response = new LoginResponse();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            Admin admin = adminRepository.findByUsername(loginRequest.getUsername());

            var jwt = jwtUtils.generateToken(admin.getUsername());
            var refreshToken = jwtUtils.generateRefreshToken(admin.getUsername());

            response.setStatus(200);
            response.setToken(jwt);
            response.setAuthority(Role.ADMIN);
            response.setUsername(admin.getUsername());
            response.setRefreshToken(refreshToken);
            response.setTokenExpirationTime("8 min");
            response.setLoginMessage("Successfully Signed In . Welcome " + admin.getRole());
        } catch (Exception e) {

            response.setStatus(500);
            response.setErrorMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);

    }
    public AdminDto addAdmin(RegistrationRequest registrationRequest) {
            AdminDto adminDto = new AdminDto();

        try {
            if (adminRepository.existsByUsername(registrationRequest.getUsername())) {
                adminDto.setStatusCode(HttpStatus.CONFLICT);
                adminDto.setMessage("Admin already exists.");
                   return adminDto;
            }
            Admin admin = new Admin();
            admin.setEmail(registrationRequest.getEmail());
            admin.setUsername(registrationRequest.getUsername());
            admin.setName(registrationRequest.getName());
            admin.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            admin.setRole(Role.ADMIN);

            if (registrationRequest != null && registrationRequest.getPassword() != null) {
                adminDto = AdminMapper.entityToDto(admin);
                adminRepository.save(admin);
            }
        } catch (Exception e) {
            adminDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            adminDto.setErrorMessage("Internal Server Error. Please try again");
        }
        return adminDto;
    }

}
