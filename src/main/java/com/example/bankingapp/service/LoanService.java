package com.example.bankingapp.service;

import com.example.bankingapp.model.Customer;
import com.example.bankingapp.model.Loan;
import com.example.bankingapp.model.LoanPayment;
import com.example.bankingapp.model.enums.LoanStatus;
import com.example.bankingapp.repository.CustomerRepository;
import com.example.bankingapp.repository.LoanPaymentRepository;
import com.example.bankingapp.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanPaymentRepository loanPaymentRepository;
    private final CustomerRepository customerRepository; // Needed to link the loan to a customer

    @Transactional
    public Loan applyForLoan(Loan loan, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found for loan application."));

        loan.setCustomer(customer);
        loan.setStatus(LoanStatus.PENDING); // Loans always start as pending
        loan.setOutstandingBalance(loan.getPrincipalAmount()); // Initially, outstanding balance is the full amount

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan makePayment(Long loanId, BigDecimal paymentAmount) {
        if (paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive.");
        }
        Loan loan = findById(loanId);
        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new RuntimeException("Payments can only be made on active loans.");
        }

        // Create the payment record
        LoanPayment payment = new LoanPayment();
        payment.setLoan(loan);
        payment.setPaymentAmount(paymentAmount);
        loanPaymentRepository.save(payment);

        // Update the loan's outstanding balance
        loan.setOutstandingBalance(loan.getOutstandingBalance().subtract(paymentAmount));

        // If the balance is zero or less, mark the loan as paid off
        if (loan.getOutstandingBalance().compareTo(BigDecimal.ZERO) <= 0) {
            loan.setStatus(LoanStatus.PAID_OFF);
        }

        return loanRepository.save(loan);
    }

    public Loan findById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));
    }

    public List<Loan> findByCustomerId(Long customerId) {
        // Use the custom repository method
        return loanRepository.findByCustomer_Id(customerId);
    }
}