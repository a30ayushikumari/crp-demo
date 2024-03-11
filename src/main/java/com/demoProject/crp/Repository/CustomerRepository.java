package com.demoProject.crp.Repository;

import com.demoProject.crp.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    boolean existsByUsername(String username);

    Customer findByUsername(String username);
}
