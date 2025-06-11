package com.example.bankingapp.controller;

import com.example.bankingapp.model.enums.AccountType;
import com.example.bankingapp.service.AccountService;
import com.example.bankingapp.service.CustomerService;
import com.example.bankingapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    @GetMapping("/form/{customerId}")
    public String showCreateAccountForm(@PathVariable Long customerId, Model model) {
        model.addAttribute("pageTitle", "New Account");
        model.addAttribute("customer", customerService.findById(customerId));
        model.addAttribute("accountTypes", AccountType.values());
        return "account/form";
    }

    @PostMapping("/save/{customerId}")
    public String createAccount(@PathVariable Long customerId, @RequestParam AccountType accountType,
            @RequestParam BigDecimal initialDeposit) {
        accountService.createAccount(customerId, accountType, initialDeposit);
        return "redirect:/customers/details/" + customerId;
    }

    @GetMapping("/details/{accountId}")
    public String viewAccount(@PathVariable Long accountId, Model model) {
        model.addAttribute("account", accountService.getAccountDetails(accountId));
        model.addAttribute("transactions", transactionService.findByAccountId(accountId));
        model.addAttribute("pageTitle", "Account Details");
        return "account/details";
    }

    @PostMapping("/{accountId}/deposit")
    public String deposit(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        accountService.deposit(accountId, amount);
        return "redirect:/accounts/details/" + accountId;
    }

    @PostMapping("/{accountId}/withdraw")
    public String withdraw(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        accountService.withdraw(accountId, amount);
        return "redirect:/accounts/details/" + accountId;
    }
}