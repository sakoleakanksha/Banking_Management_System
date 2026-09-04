package com.akanksha.bankingmanagementsystem.service;

import com.akanksha.bankingmanagementsystem.entity.Account;
import com.akanksha.bankingmanagementsystem.entity.User;
import com.akanksha.bankingmanagementsystem.repository.AccountRepository;
import com.akanksha.bankingmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {


    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    // Create Account
    public Account createAccount(Account account, Long userId) {

        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new RuntimeException("Account number already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        account.setUser(user);

        return accountRepository.save(account);
    }
    public Account updateAccount(Long id, Account updatedAccount) {

        // Find existing account
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        // Update account details
        existingAccount.setAccountNumber(
                updatedAccount.getAccountNumber()
        );

        existingAccount.setAccountType(
                updatedAccount.getAccountType()
        );

        existingAccount.setBalance(
                updatedAccount.getBalance()
        );

        // Save updated account
        return accountRepository.save(existingAccount);
    }

    // Get All Accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Get Account By ID
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Delete Account
    public void deleteAccount(Long id) {

        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found");
        }

        accountRepository.deleteById(id);
    }


}
