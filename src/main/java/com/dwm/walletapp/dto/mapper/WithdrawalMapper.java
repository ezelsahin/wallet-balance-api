package com.dwm.walletapp.dto.mapper;

import com.dwm.walletapp.dto.WithdrawalTransactionDTO;
import com.dwm.walletapp.entity.Transaction;

public class WithdrawalMapper {
    public static WithdrawalTransactionDTO toDTO (Transaction transaction) {
        WithdrawalTransactionDTO withdrawalTransactionDTO = new WithdrawalTransactionDTO();
        withdrawalTransactionDTO.setTransactionId(transaction.getTransactionId());
        withdrawalTransactionDTO.setCustomerId(transaction.getCustomerId());
        withdrawalTransactionDTO.setTransactionType(transaction.getTransactionType());
        withdrawalTransactionDTO.setWithdrawalAmount(transaction.getWithdrawalAmount());
        withdrawalTransactionDTO.setTransactionTime(transaction.getTransactionTime());
        return withdrawalTransactionDTO;
    }

    public static Transaction toEntity (WithdrawalTransactionDTO withdrawalTransactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(withdrawalTransactionDTO.getTransactionId());
        transaction.setCustomerId(withdrawalTransactionDTO.getCustomerId());
        transaction.setTransactionType(withdrawalTransactionDTO.getTransactionType());
        transaction.setWithdrawalAmount(withdrawalTransactionDTO.getWithdrawalAmount());
        transaction.setTransactionTime(withdrawalTransactionDTO.getTransactionTime());
        return transaction;
    }
}
