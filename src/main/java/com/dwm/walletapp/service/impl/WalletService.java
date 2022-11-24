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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
     *
     * @param customerId A unique numeric value which belongs to a specific customer
     * @return Returns customers wallet balance information
     */
    @Transactional
    @Override
    public WalletBalance getWalletBalance(int customerId)
    {
        WalletBalance walletBalance = walletRepository.findByCustomerId(customerId);

        if (walletBalance == null)
            throw new NotFoundException("Customer wallet");

        return walletBalance;
    }

    /**
     * Withdraws money from a customers wallet according to given withdrawal request dto if the balance amount is sufficient
     *
     * @param withdrawalTransactionDTO A given body of withdrawal transaction dto with all parameters
     * @return Returns new wallet balance of the customer after withdrawal transaction
     */
    @Transactional
    @Override
    public WalletBalance withdrawalTransaction(WithdrawalTransactionDTO withdrawalTransactionDTO)
    {
        WalletBalance wallet = validateRequest(withdrawalTransactionDTO.getTransactionId(), withdrawalTransactionDTO.getCustomerId());

        transactionReporsitory.save(WithdrawalMapper.toEntity(withdrawalTransactionDTO));

        BigDecimal withdrawalAmount = withdrawalTransactionDTO.getWithdrawalAmount();

        if (withdrawalAmount.compareTo(wallet.getWalletBalance()) == 1)
            throw new InsufficientBalanceException("Wallet balance");

        wallet.setWalletBalance(subtractBalance(wallet.getWalletBalance(), withdrawalTransactionDTO.getWithdrawalAmount()));

        return saveCustomerBalance(wallet);
    }

    /**
     * Deposits money from a customers wallet according to given deposit request dto
     *
     * @param depositTransactionDTO A given body of deposit transaction dto with all parameters
     * @return Returns new wallet balance of the customer after deposit transaction
     */
    @Transactional
    @Override
    public WalletBalance depositTransaction(DepositTransactionDTO depositTransactionDTO)
    {
        WalletBalance wallet = validateRequest(depositTransactionDTO.getTransactionId(), depositTransactionDTO.getCustomerId());

        transactionReporsitory.save(DepositMapper.toEntity(depositTransactionDTO));

        wallet.setWalletBalance(addBalance(wallet.getWalletBalance(), depositTransactionDTO.getDepositAmount()));

        return saveCustomerBalance(wallet);
    }

    /**
     * Validates if requested transaction Id is unique and if requested transaction is for an existed customer
     *
     * @param transactionId A unique numeric value which belongs to a transaction request
     * @param customerId A unique numeric value which belongs to a specific customer
     * @return Returns wallet balance which is found for given customer Id
     */
    private WalletBalance validateRequest(int transactionId, int customerId)
    {
        if (transactionReporsitory.existsById(transactionId)) {
            throw new NotUniqueException("Transaction ID");
        }

        WalletBalance wallet = walletRepository.findByCustomerId(customerId);

        if (wallet == null) {
            throw new NotFoundException("Customer Wallet");
        }
        return wallet;
    }

    /**
     * Gets all transactions of a customer according to given customer id
     *
     * @param customerId A unique numeric value which belongs to a specific customer
     * @return Returns customers every transactions as a list
     */
    @Transactional
    @Override
    public List<Transaction> getTransactionHistory(int customerId)
    {
        List<Transaction> transactions = transactionReporsitory.findByCustomerId(customerId);
        if (CollectionUtils.isEmpty(transactions)) {
            throw new NotFoundException("Customer transaction");
        }
        return transactions;
    }

    /**
     * Adds given wallet balance information to database
     *
     * @param walletBalance A given wallet balance entity body with all parameters
     * @return Returns added customers wallet balance information
     */
    @Transactional
    @Override
    public WalletBalance save(WalletBalance walletBalance)
    {
        if (walletRepository.findByCustomerId(walletBalance.getCustomerId()) != null) {
            throw new NotUniqueException("Customer ID");
        }

        return saveCustomerBalance(walletBalance);
    }

    /**
     * Updates wallet balance information in the database with current wallet balance amount
     *
     * @param walletBalance A given wallet balance entity body with all parameters
     * @return Returns updated customers wallet balance information
     */
    @Transactional
    public WalletBalance saveCustomerBalance(WalletBalance walletBalance)
    {
        return walletRepository.save(walletBalance);
    }

    /**
     * Adds given deposit amount to customers wallet balance
     *
     * @param walletBalance Balance of the wallet
     * @param depositAmount Requested deposit amount
     * @return returns new wallet balance after deposit amount added
     */
    public BigDecimal addBalance(BigDecimal walletBalance, BigDecimal depositAmount)
    {
        return walletBalance.add(depositAmount);
    }

    /**
     * Subtracts given withdrawal amount from customers wallet balance
     *
     * @param walletBalance Balance of the wallet
     * @param withdrawalAmount Requested withdrawal amount
     * @return returns new wallet balance after withdrawal amount subtracted
     */
    public BigDecimal subtractBalance(BigDecimal walletBalance, BigDecimal withdrawalAmount)
    {
        return walletBalance.subtract(withdrawalAmount);
    }
}
