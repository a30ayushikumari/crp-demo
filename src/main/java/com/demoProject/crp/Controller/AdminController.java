package com.demoProject.crp.Controller;

import com.demoProject.crp.CustomException.CustomerNotFoundException;
import com.demoProject.crp.Dto.LoginRequest;
import com.demoProject.crp.Dto.LoginResponse;
import com.demoProject.crp.Dto.RegistrationRequest;
import com.demoProject.crp.Dto.RegistrationResponse;
import com.demoProject.crp.Entity.Customer;
import com.demoProject.crp.Service.AdminService;
import com.demoProject.crp.Service.CustomerService;
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
   private   AdminService adminService;
    @Autowired
   private  CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<RegistrationResponse> signUp(@RequestBody RegistrationRequest signUpRequest) {
        System.err.println(signUpRequest.toString());
        return ResponseEntity.ok(adminService.adminSignUp(signUpRequest));
    }
    @PostMapping("/signin")
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
    @PostMapping("/addAdmin")
    public ResponseEntity<RegistrationResponse> addAdmin(@RequestBody RegistrationRequest registrationRequest) {
        return adminService.addAdmin(registrationRequest);

    }
}
