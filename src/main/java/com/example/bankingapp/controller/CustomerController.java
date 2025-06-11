package com.example.bankingapp.controller;

import com.example.bankingapp.model.Customer;
import com.example.bankingapp.service.AccountService;
import com.example.bankingapp.service.BranchService;
import com.example.bankingapp.service.CustomerService;
import com.example.bankingapp.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final BranchService branchService; // Needed for home branch assignment
    private final AccountService accountService; // Needed to show accounts on detail page
    private final LoanService loanService; // Needed to show loans on detail page

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("pageTitle", "Customers");
        model.addAttribute("customers", customerService.findAll());
        return "customer/list";
    }

    @GetMapping("/form")
    public String showCustomerForm(Model model) {
        model.addAttribute("pageTitle", "New Customer");
        model.addAttribute("customer", new Customer());
        model.addAttribute("branches", branchService.findAll());
        return "customer/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Customer");
        model.addAttribute("customer", customerService.findById(id));
        model.addAttribute("branches", branchService.findAll());
        return "customer/form";
    }

    @GetMapping("/details/{id}")
    public String showCustomerDetails(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("pageTitle", "Customer: " + customer.getFirstName() + " " + customer.getLastName());
        model.addAttribute("customer", customer);
        model.addAttribute("accounts", accountService.findByCustomerId(id));
        model.addAttribute("loans", loanService.findByCustomerId(id));
        return "customer/details";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return "redirect:/customers";
    }
}