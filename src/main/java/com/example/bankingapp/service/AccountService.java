package com.example.bankingapp.service;

import com.example.bankingapp.model.Account;
import com.example.bankingapp.model.Customer;
import com.example.bankingapp.model.Transaction;
import com.example.bankingapp.model.enums.AccountStatus;
import com.example.bankingapp.model.enums.AccountType;
import com.example.bankingapp.model.enums.TransactionType;
import com.example.bankingapp.repository.AccountRepository;
import com.example.bankingapp.repository.CustomerRepository;
import com.example.bankingapp.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Account createAccount(Long customerId, AccountType accountType, BigDecimal initialDeposit) {
        Customer primaryOwner = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found for account creation."));

        Account account = new Account();
        account.setOwners(Set.of(primaryOwner)); // Link the owner
        account.setAccountNumber(UUID.randomUUID().toString()); // Generate a unique account number
        account.setAccountType(accountType);
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(initialDeposit);

        Account savedAccount = accountRepository.save(account);

        // If there's an initial deposit, create the first transaction record
        if (initialDeposit.compareTo(BigDecimal.ZERO) > 0) {
            Transaction initialTransaction = new Transaction();
            initialTransaction.setAccount(savedAccount);
            initialTransaction.setAmount(initialDeposit);
            initialTransaction.setTransactionType(TransactionType.DEPOSIT);
            initialTransaction.setDescription("Initial deposit on account creation");
            transactionRepository.save(initialTransaction);
        }

        return savedAccount;
    }

    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        Account account = getAccountDetails(accountId);
        account.setBalance(account.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setDescription("Customer deposit");
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }

    @Transactional
    public Account withdraw(Long accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        Account account = getAccountDetails(accountId);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds for withdrawal.");
        }
        account.setBalance(account.getBalance().subtract(amount));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        // Withdrawals are often stored as positive in the transaction log,
        // but here we show an alternative where the amount reflects the ledger change
        transaction.setAmount(amount.negate());
        transaction.setDescription("Customer withdrawal");
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }

    public List<Account> findByCustomerId(Long customerId) {
        // Use the custom repository method
        return accountRepository.findByOwners_Id(customerId);
    }

    public Account getAccountDetails(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
    }
}