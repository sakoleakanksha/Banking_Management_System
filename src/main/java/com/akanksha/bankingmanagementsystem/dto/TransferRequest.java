package com.akanksha.bankingmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferRequest {

    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;

}