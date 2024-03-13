package com.demoProject.crp.Controller;

import com.demoProject.crp.CustomException.ToDoNotFoundException;
import com.demoProject.crp.Dto.*;
import com.demoProject.crp.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
//    @PostMapping("/sign-up")
//    public ResponseEntity<RegistrationResponse> signUp(@Valid @RequestBody RegistrationRequest signUpRequest) {
//
//        return ResponseEntity.ok(customerService.userSignUp(signUpRequest));
//    }
    @PostMapping("/sign-up")
    public ResponseEntity<CustomerDto> signUp(@Valid @RequestBody RegistrationRequest signUpRequest) {
        return ResponseEntity.ok(customerService.userSignUp(signUpRequest));
}
    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponse> signInUser(@RequestBody LoginRequest loginRequest) {
        return customerService.signInUser(loginRequest);
    }
    @GetMapping("/all-todos")
    public List<ToDoDto> fetchAll() {
        return customerService.fetchAll();
    }

    @GetMapping("/todo-by-id/{id}")
    public ResponseEntity<ToDoDto> fetchById(@PathVariable String id) {
        return new ResponseEntity<>(customerService.fetchById(Long.parseLong(id)), HttpStatus.OK);
    }
}
