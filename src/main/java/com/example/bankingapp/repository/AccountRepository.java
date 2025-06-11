package com.example.bankingapp.repository;

import com.example.bankingapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for Account entity. Includes a custom finder method
 * to retrieve all accounts associated with a customer's ID.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds all accounts owned by a specific customer.
     * Spring Data JPA generates the query based on the method name.
     *
     * @param customerId The ID of the customer.
     * @return A list of accounts belonging to the customer.
     */
    List<Account> findByOwners_Id(Long customerId);
    // Note: The method name 'findByOwners_Id' traverses the 'owners' collection
    // in the Account entity and looks for the 'id' field within the Customer
    // entity.
}