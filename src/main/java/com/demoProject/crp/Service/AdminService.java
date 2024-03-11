package com.demoProject.crp.Service;

import com.demoProject.crp.Dto.LoginRequest;
import com.demoProject.crp.Dto.LoginResponse;
import com.demoProject.crp.Dto.RegistrationRequest;
import com.demoProject.crp.Dto.RegistrationResponse;
import com.demoProject.crp.Entity.Admin;
import com.demoProject.crp.Entity.Role;
import com.demoProject.crp.Repository.AdminRepository;
import com.demoProject.crp.SecurityConfig.Util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public RegistrationResponse adminSignUp(RegistrationRequest signUpRequest) {

        RegistrationResponse resp = new RegistrationResponse();
        try {

            if (adminRepository.existsByUsername(signUpRequest.getEmail())) {
                resp.setHttpStatusCode(400);
                resp.setMessage("Username already exists.");
                return resp;
            }

            Admin admin = new Admin();
            admin.setUsername(signUpRequest.getUsername());
            admin.setEmail(signUpRequest.getEmail());
            admin.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            admin.setName(signUpRequest.getName());

            if (signUpRequest != null && signUpRequest.getPassword() != null) {
                Admin ourAdminResult = adminRepository.save(admin);
                resp.setAdmin(ourAdminResult);
                resp.setMessage("User Registration success");
                resp.setHttpStatusCode(200);
            }
        } catch (Exception e) {
            resp.setHttpStatusCode(500);
            resp.setErrorMessage("Internal Server Error. Try again.");

            e.printStackTrace();
        }
        return resp;
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
    public ResponseEntity<RegistrationResponse> addAdmin(RegistrationRequest registrationRequest) {
        RegistrationResponse response = new RegistrationResponse();
        Admin admin = new Admin();
        admin.setEmail(registrationRequest.getEmail());
        admin.setUsername(registrationRequest.getUsername());
        admin.setName(registrationRequest.getName());
        admin.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        admin.setRole(Role.ADMIN);

        adminRepository.save(admin);
        response.setAdmin(admin);
        response.setMessage("Admin Added Successfully");
        response.setHttpStatusCode(200);

        return ResponseEntity.ok(response);

    }

}
