package com.example.bankingapp.controller;

import com.example.bankingapp.model.Employee;
import com.example.bankingapp.service.BranchService;
import com.example.bankingapp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final BranchService branchService; // Needed for the branch dropdown

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("pageTitle", "Employees");
        model.addAttribute("employees", employeeService.findAll());
        return "employee/list";
    }

    @GetMapping("/form")
    public String showEmployeeForm(Model model) {
        model.addAttribute("pageTitle", "New Employee");
        model.addAttribute("employee", new Employee());
        model.addAttribute("branches", branchService.findAll()); // Pre-load branches
        return "employee/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Employee");
        model.addAttribute("employee", employeeService.findById(id));
        model.addAttribute("branches", branchService.findAll()); // Pre-load branches
        return "employee/form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}