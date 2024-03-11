package com.demoProject.crp.UserDetailsService;

import com.demoProject.crp.Entity.Customer;
import com.demoProject.crp.Repository.AdminRepository;
import com.demoProject.crp.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {

        Customer customer = customerRepository.findByUsername(username);
        if (customer != null)
            return customer;
        else
            return adminRepository.findByUsername(username);

    }

}

