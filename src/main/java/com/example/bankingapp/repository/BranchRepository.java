package com.example.bankingapp.repository;

import com.example.bankingapp.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Branch entity.
 * Provides standard CRUD operations through JpaRepository.
 */
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
}