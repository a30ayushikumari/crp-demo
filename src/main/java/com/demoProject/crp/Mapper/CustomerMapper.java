package com.demoProject.crp.Mapper;

import com.demoProject.crp.Dto.CustomerDto;
import com.demoProject.crp.Entity.Customer;
import org.springframework.http.HttpStatus;

public class CustomerMapper {
    public static CustomerDto entityToDto(Customer customer) {
        CustomerDto Cdto = new CustomerDto();
        Cdto.setName(customer.getName());
        Cdto.setUsername(customer.getUsername());
        Cdto.setMessage("Customer Signed up successfully");
        Cdto.setStatusCode(HttpStatus.CREATED);
        return Cdto;
    }
}
