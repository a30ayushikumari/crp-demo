package com.demoProject.crp.Repository;

import com.demoProject.crp.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    boolean existsByUsername(String username);
    Admin findByUsername(String username);
}
