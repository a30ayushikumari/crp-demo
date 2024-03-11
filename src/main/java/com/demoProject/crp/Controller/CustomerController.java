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
    @PostMapping("/signup")
    public ResponseEntity<RegistrationResponse> signUp(@Valid @RequestBody RegistrationRequest signUpRequest) {

        return ResponseEntity.ok(customerService.userSignUp(signUpRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signInUser(@RequestBody LoginRequest loginRequest) {
        return customerService.signInUser(loginRequest);
    }
    @GetMapping("/getAllTodos")
    public List<ToDoDto> fetchAll() {
        return customerService.fetchAll();
    }

    @GetMapping("/getToDoById/{id}")
    public ResponseEntity<ToDoDto> fetchById(@PathVariable String id) {
        try {
            Integer i = Integer.parseInt(id);
            System.err.println(i);

        } catch (Exception e) {
            throw new ToDoNotFoundException("Id didn't match", "Provide correct Id",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        ToDoDto todo = customerService.fetchById(Long.parseLong(id));
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }
}
