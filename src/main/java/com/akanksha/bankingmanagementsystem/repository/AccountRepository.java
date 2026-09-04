package com.akanksha.bankingmanagementsystem.repository;

import com.akanksha.bankingmanagementsystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(String accountNumber);
}