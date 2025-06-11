package com.example.bankingapp.service;

import com.example.bankingapp.model.Branch;
import com.example.bankingapp.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    public Branch findById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }

    public void deleteById(Long id) {
        branchRepository.deleteById(id);
    }
}