package com.ktsr.service.impl;

import com.ktsr.dto.AccountDto;
import com.ktsr.entity.Transaction;
import com.ktsr.feign.AccountServiceClient;
import com.ktsr.repository.TransactionRepository;
import com.ktsr.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountServiceClient accountServiceClient;

    @Override
    @Transactional
    public Transaction deposit(Long accountId, double amount, String jwt) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }

        AccountDto accountDto = accountServiceClient.getAccountById(accountId, jwt).getBody();
        if (accountDto == null) {
            throw new RuntimeException("Account not found with id: " + accountId);
        }

        accountServiceClient.updateAccountBalance(accountId, amount, jwt); // Pass delta

        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);
        transaction.setTransactionType("DEPOSIT");
        transaction.setDescription("Deposit of $ " + amount);
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Transaction withdraw(Long accountId, double amount, String jwt) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }

        AccountDto accountDto = accountServiceClient.getAccountById(accountId, jwt).getBody();
        if (accountDto == null) {
            throw new RuntimeException("Account not found with id: " + accountId);
        }
        if (accountDto.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance for withdrawal");
        }

        accountServiceClient.updateAccountBalance(accountId, -amount, jwt); // Pass negative amount

        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAWAL");
        transaction.setDescription("Withdrawal of $ " + amount);
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Transaction transfer(Long fromAccountId, Long toAccountId, double amount, String jwt) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero");
        }

        AccountDto fromAccount = accountServiceClient.getAccountById(fromAccountId, jwt).getBody();
        if (fromAccount == null) {
            throw new RuntimeException("From Account not found with id: " + fromAccountId);
        }

        AccountDto toAccount = accountServiceClient.getAccountById(toAccountId, jwt).getBody();
        if (toAccount == null) {
            throw new RuntimeException("To Account not found with id: " + toAccountId);
        }

        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance for transfer");
        }

        accountServiceClient.updateAccountBalance(fromAccountId, -amount, jwt); // debit
        accountServiceClient.updateAccountBalance(toAccountId, amount, jwt);   // credit

        Transaction transaction = new Transaction();
        transaction.setAccountId(fromAccountId);
        transaction.setAmount(amount);
        transaction.setTransactionType("TRANSFER");
        transaction.setDescription("Transfer of $ " + amount + " to account " + toAccountId);
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }
}
