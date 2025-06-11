package com.example.bankingapp.model;

import com.example.bankingapp.model.enums.LoanStatus;
import com.example.bankingapp.model.enums.LoanType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private LoanType loanType;
    private BigDecimal principalAmount;
    private BigDecimal outstandingBalance;
    private Double interestRate;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "approving_employee_id")
    private Employee approvingEmployee;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private Set<LoanPayment> payments;
}