package com.dwm.walletapp.service;

import com.dwm.walletapp.dto.DepositTransactionDTO;
import com.dwm.walletapp.dto.WithdrawalTransactionDTO;
import com.dwm.walletapp.entity.Transaction;
import com.dwm.walletapp.entity.WalletBalance;

import java.util.List;

/**
 * Service Interface for CRUD operations
 */
public interface IWalletService {
    /**
     * Calls a customers wallet balance from database
     * @param customerId A unique numeric value which belongs to a specific customer
     */
    WalletBalance getWalletBalance(int customerId);

    /**
     * Tries to complete a withdrawal transaction request with given withdrawal transaction dto body
     * @param withdrawalTransactionDTO  A given body of withdrawal transaction dto with all parameters
     */
    WalletBalance withdrawalTransaction(WithdrawalTransactionDTO withdrawalTransactionDTO);

    /**
     * Tries to complete a deposit transaction request with given deposit transaction dto body
     * @param depositTransactionDTO  A given body of deposit transaction dto with all parameters
     */
    WalletBalance depositTransaction(DepositTransactionDTO depositTransactionDTO);

    /**
     * Calls all transaction entities which belongs to a specific customer with given customer id from database
     * @param customerId A unique numeric value which belongs to a specific customer
     */
    List<Transaction> getTransactionHistory(int customerId);

    /**
     * Saves a new customers wallet balance information to database
     * @param walletBalance A given wallet balance entity with all parameters
     */
    WalletBalance save(WalletBalance walletBalance);
}
