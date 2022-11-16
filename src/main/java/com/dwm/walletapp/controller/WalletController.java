package com.dwm.walletapp.controller;

import com.dwm.walletapp.dto.DepositTransactionDTO;
import com.dwm.walletapp.dto.WithdrawalTransactionDTO;
import com.dwm.walletapp.entity.Transaction;
import com.dwm.walletapp.entity.WalletBalance;
import com.dwm.walletapp.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class WalletController {
    @Autowired
    private IWalletService iWalletService;

    /**
     * A basic test method
     * @return Returns test message
     */
    @GetMapping
    public String test() {
        return "Testing is successful!";
    }

    /**
     * @return Returns hourly average temperatures on "/getHourly" url
     */
    @GetMapping("/getBalance")
    public BigDecimal getBalance(int customerId) {
        return iWalletService.getWalletBalance(customerId);
    }

    /**
     * @return Returns daily average temperatures on "/getDaily" url
     */
    @PutMapping ("/withdrawalRequest")
    public WalletBalance newWithdrawalRequest(@RequestBody @Valid WithdrawalTransactionDTO withdrawalTransactionDTO) {
        return iWalletService.withdrawalTransaction(withdrawalTransactionDTO);
    }

    /**
     * @return Returns daily average temperatures on "/getDaily" url
     */
    @PutMapping("/depositRequest")
    public WalletBalance newDepositRequest(@RequestBody @Valid DepositTransactionDTO depositTransactionDTO) {
        return iWalletService.depositTransaction(depositTransactionDTO);
    }

    @GetMapping("/getTransactionHistory")
    public List<Transaction> getTransactions(int customerId){
        return iWalletService.getTransactionHistory(customerId);
    }

    @PutMapping("/save")
    public WalletBalance saveWallet(@RequestBody @Valid WalletBalance walletBalance){
        return iWalletService.save(walletBalance);
    }
}
