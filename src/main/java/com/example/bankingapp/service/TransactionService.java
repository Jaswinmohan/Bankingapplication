package com.example.bankingapp.service;

import com.example.bankingapp.model.Transaction;
import com.example.bankingapp.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> findByAccountId(Long accountId) {
        // Use the custom repository method to get transactions ordered by date
        return transactionRepository.findByAccount_IdOrderByTransactionDateDesc(accountId);
    }
}