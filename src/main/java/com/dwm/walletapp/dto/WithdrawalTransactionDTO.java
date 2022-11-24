package com.dwm.walletapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalTransactionDTO {
    @NotNull
    private Integer transactionId;

    @NotNull
    private Integer customerId;

    private String transactionType;

    @Min(0)
    private BigDecimal withdrawalAmount;

    private LocalDateTime transactionTime;
}
