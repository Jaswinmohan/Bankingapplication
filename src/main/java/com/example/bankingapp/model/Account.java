package com.example.bankingapp.model;

import com.example.bankingapp.model.enums.AccountStatus;
import com.example.bankingapp.model.enums.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private BigDecimal balance = BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private LocalDateTime openedDate = LocalDateTime.now();

    @ManyToMany
    @JoinTable(name = "customer_account", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<Customer> owners;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Card> cards;
}