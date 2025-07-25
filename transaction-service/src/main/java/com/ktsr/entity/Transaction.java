package com.ktsr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;


    private double amount;

    private String transactionType; // e.g., "DEPOSIT", "WITHDRAWAL", "TRANSFER"

    private String description; // Optional description of the transaction

    private LocalDateTime transactionDate; // Timestamp of the transaction
}
