package com.ktsr.repository;

import com.ktsr.enity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

//    @Query("SELECT a FROM Account a WHERE a.userId = :userId")
    List<Account> findByUserId(Long userId);

    Optional<Account> findByAccountNumber(String accountNumber);
}
