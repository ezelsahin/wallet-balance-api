package com.dwm.walletapp.dto.mapper;

import com.dwm.walletapp.dto.DepositTransactionDTO;
import com.dwm.walletapp.entity.Transaction;

public class DepositMapper {
    public static DepositTransactionDTO toDTO (Transaction transaction) {
        DepositTransactionDTO depositTransactionDTO = new DepositTransactionDTO();
        depositTransactionDTO.setTransactionId(transaction.getTransactionId());
        depositTransactionDTO.setCustomerId(transaction.getCustomerId());
        depositTransactionDTO.setTransactionType(transaction.getTransactionType());
        depositTransactionDTO.setDepositAmount(transaction.getDepositAmount());
        depositTransactionDTO.setTransactionTime(transaction.getTransactionTime());
        return depositTransactionDTO;
    }

    public static Transaction toEntity (DepositTransactionDTO depositTransactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(depositTransactionDTO.getTransactionId());
        transaction.setCustomerId(depositTransactionDTO.getCustomerId());
        transaction.setTransactionType(depositTransactionDTO.getTransactionType());
        transaction.setDepositAmount(depositTransactionDTO.getDepositAmount());
        transaction.setTransactionTime(depositTransactionDTO.getTransactionTime());
        return transaction;
    }
}
