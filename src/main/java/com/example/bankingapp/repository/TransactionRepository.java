package com.example.bankingapp.repository;

import com.example.bankingapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for the Transaction entity. Includes a custom finder method
 * to fetch an account's statement in chronological order.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds all transactions for a specific account, ordered by the
     * transaction date in descending order (most recent first).
     *
     * @param accountId The ID of the account.
     * @return An ordered list of transactions for the account.
     */
    List<Transaction> findByAccount_IdOrderByTransactionDateDesc(Long accountId);
}