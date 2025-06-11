package com.example.bankingapp.repository;

import com.example.bankingapp.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Card entity.
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}