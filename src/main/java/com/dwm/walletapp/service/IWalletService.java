package com.dwm.walletapp.service;

import com.dwm.walletapp.dto.DepositTransactionDTO;
import com.dwm.walletapp.dto.WithdrawalTransactionDTO;
import com.dwm.walletapp.entity.Transaction;
import com.dwm.walletapp.entity.WalletBalance;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service Interface for CRUD operations
 */
public interface IWalletService {
    /**
     * Calls a customers wallet balance from database
     */
    BigDecimal getWalletBalance(int customerId);

    WalletBalance withdrawalTransaction(WithdrawalTransactionDTO withdrawalTransactionDTO);

    WalletBalance depositTransaction(DepositTransactionDTO depositTransactionDTO);

    List<Transaction> getTransactionHistory(int customerId);

    WalletBalance save(WalletBalance walletBalance);
}
