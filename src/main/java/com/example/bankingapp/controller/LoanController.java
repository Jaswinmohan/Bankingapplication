package com.example.bankingapp.controller;

import com.example.bankingapp.model.Loan;
import com.example.bankingapp.model.enums.LoanType;
import com.example.bankingapp.service.CustomerService;
import com.example.bankingapp.service.EmployeeService;
import com.example.bankingapp.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    @GetMapping("/form/{customerId}")
    public String showLoanForm(@PathVariable Long customerId, Model model) {
        model.addAttribute("pageTitle", "New Loan Application");
        model.addAttribute("loan", new Loan());
        model.addAttribute("customer", customerService.findById(customerId));
        model.addAttribute("employees", employeeService.findAll()); // For selecting a loan officer
        model.addAttribute("loanTypes", LoanType.values());
        return "loan/form";
    }

    @PostMapping("/save/{customerId}")
    public String saveLoan(@ModelAttribute Loan loan, @PathVariable Long customerId) {
        loanService.applyForLoan(loan, customerId);
        return "redirect:/customers/details/" + customerId;
    }

    @GetMapping("/details/{loanId}")
    public String showLoanDetails(@PathVariable Long loanId, Model model) {
        model.addAttribute("pageTitle", "Loan Details");
        model.addAttribute("loan", loanService.findById(loanId));
        return "loan/details";
    }

    @PostMapping("/{loanId}/payment")
    public String makeLoanPayment(@PathVariable Long loanId, @RequestParam BigDecimal paymentAmount) {
        loanService.makePayment(loanId, paymentAmount);
        return "redirect:/loans/details/" + loanId;
    }
}