package com.akanksha.bankingmanagementsystem.controller;

import com.akanksha.bankingmanagementsystem.dto.TransferRequest;
import com.akanksha.bankingmanagementsystem.entity.Transaction;
import com.akanksha.bankingmanagementsystem.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Deposit Money
    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<Transaction> deposit(
            @PathVariable Long accountId,
            @RequestParam Double amount) {

        Transaction transaction =
                transactionService.deposit(accountId, amount);

        return ResponseEntity.ok(transaction);
    }

    // Withdraw Money
    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<Transaction> withdraw(
            @PathVariable Long accountId,
            @RequestParam Double amount) {

        Transaction transaction =
                transactionService.withdraw(accountId, amount);

        return ResponseEntity.ok(transaction);
    }
    // Get Transaction History by Account ID
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(
            @PathVariable Long accountId) {

        return ResponseEntity.ok(
                transactionService.getTransactionsByAccountId(accountId)
        );
    }
    // Transfer Money
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(
            @RequestBody TransferRequest request) {

        transactionService.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount()
        );

        return ResponseEntity.ok("Money transferred successfully");
    }
}