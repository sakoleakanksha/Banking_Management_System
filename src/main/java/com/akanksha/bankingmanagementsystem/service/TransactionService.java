package com.akanksha.bankingmanagementsystem.service;

import com.akanksha.bankingmanagementsystem.dto.TransferRequest;
import com.akanksha.bankingmanagementsystem.entity.Account;
import com.akanksha.bankingmanagementsystem.entity.Transaction;
import com.akanksha.bankingmanagementsystem.repository.AccountRepository;
import com.akanksha.bankingmanagementsystem.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository) {

        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }


    // Deposit Money
    public Transaction deposit(Long accountId, Double amount) {

        // Find account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        // Validate amount
        if (amount == null || amount <= 0) {
            throw new RuntimeException(
                    "Deposit amount must be greater than zero"
            );
        }

        // Update balance
        account.setBalance(account.getBalance() + amount);

        // Save updated account
        accountRepository.save(account);

        // Create transaction
        Transaction transaction = new Transaction();

        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }


    // Withdraw Money
    public Transaction withdraw(Long accountId, Double amount) {

        // Find account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        // Validate amount
        if (amount == null || amount <= 0) {
            throw new RuntimeException(
                    "Withdraw amount must be greater than zero"
            );
        }

        // Check sufficient balance
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update balance
        account.setBalance(account.getBalance() - amount);

        // Save updated account
        accountRepository.save(account);

        // Create transaction
        Transaction transaction = new Transaction();

        transaction.setTransactionType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }


    // Get Transaction History
    public List<Transaction> getTransactionsByAccountId(Long accountId) {

        return transactionRepository.findByAccountId(accountId);
    }


    // Transfer Money
    public Transaction transfer(TransferRequest request) {

        // Validate amount
        if (request.getAmount() == null || request.getAmount() <= 0) {
            throw new RuntimeException(
                    "Transfer amount must be greater than zero"
            );
        }

        // Prevent transfer to same account
        if (request.getFromAccountId()
                .equals(request.getToAccountId())) {

            throw new RuntimeException(
                    "Cannot transfer money to the same account"
            );
        }

        // Find sender account
        Account fromAccount =
                accountRepository.findById(request.getFromAccountId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sender account not found"
                                ));

        // Find receiver account
        Account toAccount =
                accountRepository.findById(request.getToAccountId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Receiver account not found"
                                ));

        // Check sufficient balance
        if (fromAccount.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        // Deduct money from sender
        fromAccount.setBalance(
                fromAccount.getBalance() - request.getAmount()
        );

        // Add money to receiver
        toAccount.setBalance(
                toAccount.getBalance() + request.getAmount()
        );

        // Save both accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Create transaction
        Transaction transaction = new Transaction();

        transaction.setTransactionType("TRANSFER");
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAccount(fromAccount);

        // Save transaction
        return transactionRepository.save(transaction);
    }
    public void transfer(
            Long fromAccountId,
            Long toAccountId,
            Double amount) {

        // Validate amount
        if (amount == null || amount <= 0) {
            throw new RuntimeException(
                    "Transfer amount must be greater than zero"
            );
        }

        // Prevent transfer to same account
        if (fromAccountId.equals(toAccountId)) {
            throw new RuntimeException(
                    "Cannot transfer money to the same account"
            );
        }

        // Find sender account
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() ->
                        new RuntimeException("Sender account not found"));

        // Find receiver account
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() ->
                        new RuntimeException("Receiver account not found"));

        // Check balance
        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // Deduct money
        fromAccount.setBalance(
                fromAccount.getBalance() - amount
        );

        // Add money
        toAccount.setBalance(
                toAccount.getBalance() + amount
        );

        // Save both accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Create withdrawal transaction
        Transaction withdrawTransaction = new Transaction();

        withdrawTransaction.setAccount(fromAccount);
        withdrawTransaction.setAmount(amount);
        withdrawTransaction.setTransactionType("TRANSFER_OUT");
        withdrawTransaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(withdrawTransaction);

        // Create deposit transaction
        Transaction depositTransaction = new Transaction();

        depositTransaction.setAccount(toAccount);
        depositTransaction.setAmount(amount);
        depositTransaction.setTransactionType("TRANSFER_IN");
        depositTransaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(depositTransaction);
    }
}