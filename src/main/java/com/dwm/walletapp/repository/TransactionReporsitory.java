package com.dwm.walletapp.repository;

import com.dwm.walletapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionReporsitory extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByCustomerId(int customerId);

    boolean findByTransactionId(int transactionId);
}
