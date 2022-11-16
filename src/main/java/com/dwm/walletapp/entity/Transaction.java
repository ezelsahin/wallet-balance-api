package com.dwm.walletapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int transactionId;

    @Column
    private int customerId;

    @Column
    private char transactionType;

    @Column
    private BigDecimal withdrawalAmount;

    @Column
    private BigDecimal depositAmount;

    @Column
    private LocalDateTime transactionTime;
}
