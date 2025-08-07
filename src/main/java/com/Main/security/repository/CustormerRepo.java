package com.Main.security.repository;

import com.Main.security.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustormerRepo extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
}
