package com.example.bankingapp.repository;

import com.example.bankingapp.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for the Loan entity. Includes a custom finder method
 * to retrieve all loans for a given customer.
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    /**
     * Finds all loans associated with a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of loans for the customer.
     */
    List<Loan> findByCustomer_Id(Long customerId);
}