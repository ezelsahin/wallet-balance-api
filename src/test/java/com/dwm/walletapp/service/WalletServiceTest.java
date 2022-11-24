package com.dwm.walletapp.service;

import com.dwm.walletapp.dto.DepositTransactionDTO;
import com.dwm.walletapp.dto.WithdrawalTransactionDTO;
import com.dwm.walletapp.entity.Transaction;
import com.dwm.walletapp.entity.WalletBalance;
import com.dwm.walletapp.repository.TransactionReporsitory;
import com.dwm.walletapp.repository.WalletRepository;
import com.dwm.walletapp.service.impl.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {
    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionReporsitory transactionReporsitory;

    @InjectMocks
    private WalletService walletService;

    @Test
    void getWalletBalance() {
        WalletBalance sampleWallet = new WalletBalance(1, 123, BigDecimal.valueOf(125.65));

        when(walletRepository.findByCustomerId(123)).thenReturn(sampleWallet);

        WalletBalance actualBalance = walletService.getWalletBalance(123);

        assertEquals(actualBalance.getWalletBalance(), sampleWallet.getWalletBalance());

    }

    @Test
    void withdrawalTransaction(){
        WalletBalance sampleWallet = new WalletBalance(1, 12, BigDecimal.valueOf(485.25));
        WithdrawalTransactionDTO sampleWithdrawalDTO = new WithdrawalTransactionDTO(123, 12, "W", BigDecimal.valueOf(128.0), LocalDateTime.of(2022, 5, 28, 12, 28));

        when(walletRepository.findByCustomerId(12)).thenReturn(sampleWallet);

        BigDecimal actualBalance = walletService.withdrawalTransaction(sampleWithdrawalDTO).getWalletBalance();

        assertEquals(actualBalance, BigDecimal.valueOf(485.25 - 128.0));

    }

    @Test
    void depositTransaction(){
        WalletBalance sampleWallet = new WalletBalance(1, 15, BigDecimal.valueOf(745.08));
        DepositTransactionDTO sampleDepositDTO = new DepositTransactionDTO(127, 15, "D", BigDecimal.valueOf(586.4), LocalDateTime.of(2022, 6, 24, 15, 58));

        when(walletRepository.findByCustomerId(15)).thenReturn(sampleWallet);

        BigDecimal actualBalance = walletService.depositTransaction(sampleDepositDTO).getWalletBalance();

        assertEquals(actualBalance, BigDecimal.valueOf(745.08 + 586.4));

    }

    @Test
    void getTransactionHistory(){
        List<Transaction> transactionList = new ArrayList<>();
        Transaction sample1 = new Transaction(1, 210, 107, "W", BigDecimal.valueOf(425.5), null, LocalDateTime.of(2022,3,15,8,42));
        Transaction sample2 = new Transaction(2, 234, 107, "D", null, BigDecimal.valueOf(1500.0), LocalDateTime.of(2022,4,1,16,3));
        Transaction sample3 = new Transaction(3, 276, 107, "D", null, BigDecimal.valueOf(167.5), LocalDateTime.of(2022,8,22,18,37));
        transactionList.add(sample1);
        transactionList.add(sample2);
        transactionList.add(sample3);

        when(transactionReporsitory.findByCustomerId(107)).thenReturn(transactionList);

        List<Transaction> actualList = walletService.getTransactionHistory(107);

        assertEquals(actualList.size(), transactionList.size());
    }

    @Test
    void save(){
        WalletBalance sampleWallet = new WalletBalance(1, 14, BigDecimal.valueOf(45200.0));

        when(walletRepository.save(sampleWallet)).thenReturn(sampleWallet);

        walletService.save(sampleWallet);

        verify(walletRepository, times(1)).save(sampleWallet);

    }
}
