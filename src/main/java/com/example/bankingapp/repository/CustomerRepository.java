package com.example.bankingapp.repository;

import com.example.bankingapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Customer entity.
 * Provides standard CRUD operations.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}