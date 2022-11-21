package com.dwm.walletapp.repository;

import com.dwm.walletapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionReporsitory extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByCustomerId(int customerId);

    String findByTransactionId(int transactionId);

}
