package com.demoProject.crp.Controller;

import com.demoProject.crp.CustomException.CustomerNotFoundException;
import com.demoProject.crp.Dto.*;
import com.demoProject.crp.Entity.Admin;
import com.demoProject.crp.Entity.Customer;
import com.demoProject.crp.Service.AdminService;
import com.demoProject.crp.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
   private AdminService adminService;
    @Autowired
   private CustomerService customerService;

//    @PostMapping("/sign-up")
//    public ResponseEntity<AdminDto> signUp(@Valid @RequestBody RegistrationRequest signUpRequest) {
//        System.err.println(signUpRequest.toString());
//        return ResponseEntity.ok(adminService.adminSignUp(signUpRequest));
//    }
    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponse> signin(@RequestBody LoginRequest loginRequest) {
        return adminService.signInAdmin(loginRequest);
    }
    @GetMapping("/show-all-customer")
    public List<Customer> getAllCustomers() {
        List<Customer> custom=customerService.getAllCustomers();

        if(custom!=null && custom.size()>0)
            return custom;

        throw new CustomerNotFoundException("No Customers Found", "Try Again!", HttpStatus.NOT_FOUND);

    }
    @PostMapping("/add-admin")
    public ResponseEntity<AdminDto> addAdmin(@Valid  @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(adminService.addAdmin(registrationRequest));
    }
}
