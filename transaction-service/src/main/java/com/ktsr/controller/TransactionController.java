package com.ktsr.controller;

import com.ktsr.entity.Transaction;
import com.ktsr.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<Transaction> deposit(@PathVariable Long accountId,
                                               @RequestParam double amount,
                                               @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(transactionService.deposit(accountId, amount, authHeader));
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<Transaction> withdraw(@PathVariable Long accountId,
                                                @RequestParam double amount,
                                                @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(transactionService.withdraw(accountId, amount, authHeader));
    }

    @PostMapping("/transfer/{fromAccountId}/to/{toAccountId}")
    public ResponseEntity<Transaction> transfer(@PathVariable Long fromAccountId,
                                                @PathVariable Long toAccountId,
                                                @RequestParam double amount,
                                                @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(transactionService.transfer(fromAccountId, toAccountId, amount, authHeader));
    }
}
