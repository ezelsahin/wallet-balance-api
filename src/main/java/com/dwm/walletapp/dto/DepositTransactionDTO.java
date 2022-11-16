package com.dwm.walletapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositTransactionDTO {
    private int transactionId;

    private int customerId;

    private char transactionType;

    private BigDecimal depositAmount;

    private LocalDateTime transactionTime;
}