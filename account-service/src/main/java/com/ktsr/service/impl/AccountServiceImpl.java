package com.ktsr.service.impl;

import com.ktsr.enity.Account;
import com.ktsr.repository.AccountRepository;
import com.ktsr.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl  implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(Long userId, Account account, String requestRole) {

        if(!requestRole.equalsIgnoreCase("ADMIN")){
            throw new RuntimeException("Unauthorized to create account");
        }
        account.setUserId(userId);
        return accountRepository.save(account);
    }


    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
    }

    @Override
    public Account updateAccount(Long accountId, Account account, String requestRole) {
        if(!requestRole.equalsIgnoreCase("ADMIN")){
            throw new RuntimeException("Unauthorized to update account");
        }
        Account existingAccount = getAccountById(accountId);
        if(account.getAccountNumber() != null) {
            existingAccount.setAccountNumber(account.getAccountNumber());
        }
        if(account.getIfscCode() != null) {
            existingAccount.setIfscCode(account.getIfscCode());
        }
        if(account.getAccountType() != null) {
            existingAccount.setAccountType(account.getAccountType());
        }
        if(account.getBalance() != null) {
            existingAccount.setBalance(account.getBalance());
        }
        return accountRepository.save(existingAccount);
    }

    @Override
    public Account deleteAccount(Long accountId, String requestRole) {
        if(!requestRole.equalsIgnoreCase("ADMIN")){
            throw new RuntimeException("Unauthorized to update account");
        }
        Account account = getAccountById(accountId);
        accountRepository.delete(account);
        return account;
    }

    @Override
    public List<Account> getAccountByUserId(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        if (accounts == null || accounts.isEmpty()) {
            throw new RuntimeException("No accounts found for user with id: " + userId);
        }
        return accounts;
    }



    @Override
    public Account deposit(Long accountId, Double amount) {
        Account account = getAccountById(accountId);
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    @Override
    public Account withdraw(Long accountId, Double amount) {
        Account account = getAccountById(accountId);
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for withdrawal");
        }
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    @Override
    public Account transfer(Long fromAccountId, Long toAccountId, Double amount) {
    Account fromAccount = getAccountById(fromAccountId);
    Account toAccount = getAccountById(toAccountId);
    if (amount <= 0) {
        throw new IllegalArgumentException("Transfer amount must be greater than zero");
    }
    if (fromAccount.getBalance() < amount) {
        throw new IllegalArgumentException("Insufficient balance for transfer");
    }
    fromAccount.setBalance(fromAccount.getBalance() - amount);
    toAccount.setBalance(toAccount.getBalance() + amount);
    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);
    return fromAccount;
    }


    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found with account number: " + accountNumber));
    }
}
