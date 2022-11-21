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
     * Gets a specific wallet balance from database
     * @return Returns
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

    @Override
    public List<Transaction> getTransactionHistory(int customerId) {
        try{
            return transactionReporsitory.findByCustomerId(customerId);
        } catch (NotFoundException ex) {
            throw new NotFoundException("Customer transaction");
        }
    }

    @Override
    public WalletBalance save(WalletBalance walletBalance) {
        return walletRepository.save(walletBalance);
    }

    public boolean isUnique(int transactionId){
        if (transactionReporsitory.findByTransactionId(transactionId) == "null" )
            return false;
        else
            return true;
    }

    public boolean isSufficient(int customerId, BigDecimal withdrawalAmount) {
        if (withdrawalAmount.compareTo(walletRepository.findByCustomerId(customerId).getWalletBalance()) == 1)
            return false;
        else
            return true;
    }

    public BigDecimal addBalance(int customerId, BigDecimal depositAmount){
        return walletRepository.findByCustomerId(customerId).getWalletBalance().add(depositAmount);
    }

    public BigDecimal subtractBalance(int customerId, BigDecimal withdrawalAmount){
        return walletRepository.findByCustomerId(customerId).getWalletBalance().subtract(withdrawalAmount);
    }
}
