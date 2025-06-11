package com.example.bankingapp.repository;

import com.example.bankingapp.model.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for LoanPayment entity.
 */
@Repository
public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Long> {
}