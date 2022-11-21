package com.dwm.walletapp.service.impl;

import com.dwm.walletapp.dto.DepositTransactionDTO;
import com.dwm.walletapp.dto.WithdrawalTransactionDTO;
import com.dwm.walletapp.dto.mapper.DepositMapper;
import com.dwm.walletapp.dto.mapper.WithdrawalMapper;
import com.dwm.walletapp.entity.Transaction;
import com.dwm.walletapp.entity.WalletBalance;
import com.dwm.walletapp.exception.InsufficientBalanceException;
import com.dwm.walletapp.exception.NotFoundException;
import com.dwm.walletapp.exception.NotUniqueException;
import com.dwm.walletapp.repository.TransactionReporsitory;
import com.dwm.walletapp.repository.WalletRepository;
import com.dwm.walletapp.service.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service that contains CRUD operations methods
 */
@Service
@RequiredArgsConstructor
public class WalletService implements IWalletService {
    @Autowired
    public TransactionReporsitory transactionReporsitory;

    @Autowired
    public WalletRepository walletRepository;

    /**
     * Gets a customers wallet balance information from database with given customer id
     * @param customerId A unique numeric value which belongs to a specific customer
     * @return Returns customers wallet balance information
     */
    @Override
    public BigDecimal getWalletBalance(int customerId)
    {
        try{
            return walletRepository.findByCustomerId(customerId).getWalletBalance();
        } catch (NotFoundException ex) {
            throw new NotFoundException("Customer Id");
        }
    }

    /**
     * Withdraws money from a customers wallet according to given withdrawal request dto
     * @param withdrawalTransactionDTO  A given body of withdrawal transaction dto with all parameters
     * @return Returns new wallet balance of the customer after withdrawal transaction
     */
    @Override
    public WalletBalance withdrawalTransaction(WithdrawalTransactionDTO withdrawalTransactionDTO) {
        int transactionId = withdrawalTransactionDTO.getTransactionId();

        try{
            isUnique(transactionId);
        } catch (NotUniqueException ex) {
            throw new NotUniqueException("Transaction Id");
        }

        int customerId = withdrawalTransactionDTO.getCustomerId();

        try{
            isSufficient(customerId, withdrawalTransactionDTO.getWithdrawalAmount());
        } catch (InsufficientBalanceException ex) {
            throw new InsufficientBalanceException("Wallet balance");
        }

        WalletBalance wallet = walletRepository.findByCustomerId(customerId);

        wallet.setWalletBalance(subtractBalance(customerId, withdrawalTransactionDTO.getWithdrawalAmount()));

        transactionReporsitory.save(WithdrawalMapper.toEntity(withdrawalTransactionDTO));

        return wallet;
    }

    /**
     * Deposits money from a customers wallet according to given deposit request dto
     * @param depositTransactionDTO A given body of deposit transaction dto with all parameters
     * @return Returns new wallet balance of the customer after deposit transaction
     */
    @Override
    public WalletBalance depositTransaction(DepositTransactionDTO depositTransactionDTO) {
        int transactionId = depositTransactionDTO.getTransactionId();

        try{
            isUnique(transactionId);
            } catch (NotUniqueException ex) {
            throw new NotUniqueException("Transaction Id");
        }

        WalletBalance wallet = walletRepository.findByCustomerId(depositTransactionDTO.getCustomerId());

        wallet.setWalletBalance(addBalance(depositTransactionDTO.getCustomerId(), depositTransactionDTO.getDepositAmount()));

        transactionReporsitory.save(DepositMapper.toEntity(depositTransactionDTO));

        return wallet;
    }

    /**
     * Gets all transactions of a customer according to given customer id
     * @param customerId  A unique numeric value which belongs to a specific customer
     * @return Returns customers every transactions as a list
     */
    @Override
    public List<Transaction> getTransactionHistory(int customerId) {
        try{
            return transactionReporsitory.findByCustomerId(customerId);
        } catch (NotFoundException ex) {
            throw new NotFoundException("Customer transaction");
        }
    }

    /**
     * Adds given wallet balance information to database
     * @param walletBalance A given wallet balance entity body with all parameters
     * @return Returns added customers wallet balance information
     */
    @Override
    public WalletBalance save(WalletBalance walletBalance) {
        return walletRepository.save(walletBalance);
    }

    /**
     * Checks if given transaction id is unique or not
     * @param transactionId A specific numeric value which belongs to a transaction request
     * @return returns true if given transaction id is exist in database
     */
    public boolean isUnique(int transactionId){
        if (transactionReporsitory.findByTransactionId(transactionId) == "null" )
            return false;
        else
            return true;
    }

    /**
     * Checks if requested withdrawal amount is bigger than wallet balance or not
     * @param customerId  A unique numeric value which belongs to a specific customer
     * @param withdrawalAmount Requested withdrawal amount
     * @return returns true if given withdrawal amount is not bigger than wallet balance
     */
    public boolean isSufficient(int customerId, BigDecimal withdrawalAmount) {
        if (withdrawalAmount.compareTo(walletRepository.findByCustomerId(customerId).getWalletBalance()) == 1)
            return false;
        else
            return true;
    }

    /**
     * Adds given deposit amount to customers wallet balance
     * @param customerId  A unique numeric value which belongs to a specific customer
     * @param depositAmount Requested deposit amount
     * @return returns new wallet balance after deposit amount added
     */
    public BigDecimal addBalance(int customerId, BigDecimal depositAmount){
        return walletRepository.findByCustomerId(customerId).getWalletBalance().add(depositAmount);
    }

    /**
     * Subtracts given withdrawal amount from customers wallet balance
     * @param customerId  A unique numeric value which belongs to a specific customer
     * @param withdrawalAmount Requested withdrawal amount
     * @return returns new wallet balance after withdrawal amount subtracted
     */
    public BigDecimal subtractBalance(int customerId, BigDecimal withdrawalAmount){
        return walletRepository.findByCustomerId(customerId).getWalletBalance().subtract(withdrawalAmount);
    }
}
