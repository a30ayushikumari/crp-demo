package com.demoProject.crp.Service;

import com.demoProject.crp.CustomException.ToDoNotFoundException;
import com.demoProject.crp.Dto.*;
import com.demoProject.crp.Entity.Customer;
import com.demoProject.crp.Entity.Role;
import com.demoProject.crp.Repository.CustomerRepository;
import com.demoProject.crp.SecurityConfig.Util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private  CustomerRepository customerRepository;
    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired

    private  JwtUtils jwtUtils;

    @Value("${jsonplaceholder.app.url}")
    private String url;

    private RestTemplate restTemplate = new RestTemplate();


    public RegistrationResponse userSignUp(RegistrationRequest signUpRequest) {

        RegistrationResponse resp = new RegistrationResponse();
        try {

            if (customerRepository.existsByUsername(signUpRequest.getUsername())) {
                resp.setHttpStatusCode(400);
                resp.setMessage("Username already exists.");
                return resp;
            }

            Customer customer = new Customer();
            customer.setEmail(signUpRequest.getEmail());
            customer.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            customer.setUsername(signUpRequest.getUsername());
            customer.setName(signUpRequest.getName());

            if (signUpRequest != null && signUpRequest.getPassword() != null) {

                Customer ourCustomerResult = customerRepository.save(customer);
                resp.setCustomer(ourCustomerResult);
                resp.setMessage("Customer Registration Success");
                resp.setHttpStatusCode(200);

            }
        } catch (Exception e) {
            resp.setHttpStatusCode(500);
            resp.setErrorMessage("Internal Server Error. Please try again.");

            e.printStackTrace();
        }
        return resp;
    }

    public ResponseEntity<LoginResponse> signInUser(LoginRequest loginRequest) {
        System.err.println("Getting in SigninUser");
        LoginResponse response = new LoginResponse();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            Customer customer = customerRepository.findByUsername(loginRequest.getUsername());

            var jwt = jwtUtils.generateToken(customer.getUsername());
            var refreshToken = jwtUtils.generateRefreshToken(customer.getUsername());

            response.setStatus(200);
            response.setToken(jwt);
            response.setUsername(customer.getUsername());
            response.setAuthority(Role.CUSTOMER);
            response.setRefreshToken(refreshToken);
            response.setTokenExpirationTime("24Hr");
            response.setLoginMessage("Successfully Signed In " + customer.getRole());
        } catch (Exception e) {

            response.setStatus(500);
            response.setErrorMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);

    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<ToDoDto> fetchAll() {

        ResponseEntity<List<ToDoDto>> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ToDoDto>>() {
                    });

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ToDoNotFoundException("Wrong Path ", "Please Check the url",
                        HttpStatus.BAD_REQUEST);
            }
        }
        return responseEntity.getBody();
    }

    public ToDoDto fetchById(Long todoId) {
        String apiUrl = url + "/" + todoId;

        ResponseEntity<ToDoDto> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    ToDoDto.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ToDoNotFoundException("ID not found: " + todoId,
                        "Try again",
                        HttpStatus.NOT_FOUND);
            }
        }

        return responseEntity.getBody();
    }
}
