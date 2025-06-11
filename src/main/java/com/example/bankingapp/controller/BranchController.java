package com.example.bankingapp.controller;

import com.example.bankingapp.model.Branch;
import com.example.bankingapp.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @GetMapping
    public String listBranches(Model model) {
        model.addAttribute("pageTitle", "Branches");
        model.addAttribute("branches", branchService.findAll());
        return "branch/list"; // Points to /templates/branch/list.html
    }

    @GetMapping("/form")
    public String showBranchForm(Model model) {
        model.addAttribute("pageTitle", "New Branch");
        model.addAttribute("branch", new Branch());
        return "branch/form"; // Points to /templates/branch/form.html
    }

    // ... all other methods are the same, but the pattern of returning the content
    // page path is key.
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Branch");
        model.addAttribute("branch", branchService.findById(id));
        return "branch/form";
    }

    @PostMapping("/save")
    public String saveBranch(@ModelAttribute Branch branch) {
        branchService.save(branch);
        return "redirect:/branches";
    }

    @GetMapping("/delete/{id}")
    public String deleteBranch(@PathVariable Long id) {
        branchService.deleteById(id);
        return "redirect:/branches";
    }
}