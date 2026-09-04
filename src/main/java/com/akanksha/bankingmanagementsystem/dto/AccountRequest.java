package com.akanksha.bankingmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {

    private Long userId;
    private String accountNumber;
    private String accountType;
    private Double balance;
}