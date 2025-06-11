package com.example.bankingapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    @Column(unique = true)
    private String transitNumber;

    @OneToMany(mappedBy = "branch")
    private Set<Employee> employees;
}