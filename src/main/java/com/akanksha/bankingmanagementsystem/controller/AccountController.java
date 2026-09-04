package com.akanksha.bankingmanagementsystem.controller;

import com.akanksha.bankingmanagementsystem.dto.AccountRequest;
import com.akanksha.bankingmanagementsystem.entity.Account;
import com.akanksha.bankingmanagementsystem.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Create Account
    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestBody AccountRequest request) {

        Account account = new Account();

        account.setAccountNumber(request.getAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getBalance());

        Account savedAccount =
                accountService.createAccount(account, request.getUserId());

        return ResponseEntity.ok(savedAccount);
    }

    // Get All Accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {

        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // Get Account By ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                accountService.getAccountById(id)
        );
    }

    // Update Account
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(
            @PathVariable Long id,
            @RequestBody Account account) {

        Account updatedAccount =
                accountService.updateAccount(id, account);

        return ResponseEntity.ok(updatedAccount);
    }

    // Delete Account
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(
            @PathVariable Long id) {

        accountService.deleteAccount(id);

        return ResponseEntity.ok("Account deleted successfully");
    }
}