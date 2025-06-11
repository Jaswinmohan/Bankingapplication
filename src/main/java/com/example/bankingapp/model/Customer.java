package com.example.bankingapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(unique = true, name = "ssn")
    private String socialSecurityNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_branch_id")
    private Branch homeBranch;
    @ManyToMany(mappedBy = "owners")
    private Set<Account> accounts;
    @OneToMany(mappedBy = "customer")
    private Set<Loan> loans;
}