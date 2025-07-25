package com.ktsr.service;

import com.ktsr.entity.Transaction;

public interface TransactionService {

    Transaction deposit(Long accountId, double amount, String jwt);

    Transaction withdraw(Long accountId, double amount, String jwt);



    Transaction transfer(Long fromAccountId, Long toAccountId, double amount, String jwt);
}
