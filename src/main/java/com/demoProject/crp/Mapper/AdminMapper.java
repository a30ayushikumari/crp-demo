package com.demoProject.crp.Mapper;

import com.demoProject.crp.Dto.AdminDto;
import com.demoProject.crp.Dto.CustomerDto;
import com.demoProject.crp.Entity.Admin;
import com.demoProject.crp.Entity.Customer;
import org.springframework.http.HttpStatus;

public class AdminMapper {
    public static AdminDto entityToDto(Admin admin) {
        AdminDto Adto = new AdminDto();
        Adto.setName(admin.getName());
        Adto.setUsername(admin.getUsername());
        Adto.setMessage("Admin Signed up successfully");
        Adto.setStatusCode(HttpStatus.CREATED);
        return Adto;
    }
}
