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
import javax.validation.constraints.Min;
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
    @GetMapping("/test")
    public String test() {
        return "Testing is successful!";
    }

    /**
     * @param customerId A unique numeric value which belongs to a specific customer
     * @return Returns customers current wallet balance with given customerId  on "/getBalance" url
     */
    @GetMapping("/getBalance/{customerId}")
    public BigDecimal getBalance(@PathVariable @Min(1) int customerId) {
        return iWalletService.getWalletBalance(customerId);
    }

    /**
     * @return
     */
    @PutMapping ("/withdrawalRequest")
    public WalletBalance newWithdrawalRequest(@RequestBody @Valid WithdrawalTransactionDTO withdrawalTransactionDTO) {
        return iWalletService.withdrawalTransaction(withdrawalTransactionDTO);
    }

    /**
     * @return
     */
    @PutMapping("/depositRequest")
    public WalletBalance newDepositRequest(@RequestBody @Valid DepositTransactionDTO depositTransactionDTO) {
        return iWalletService.depositTransaction(depositTransactionDTO);
    }

    @GetMapping("/getTransactionHistory/{customerId}")
    public List<Transaction> getTransactions(@PathVariable @Min(1) int customerId){
        return iWalletService.getTransactionHistory(customerId);
    }

    /**
     * Adds new customer ID and wallet balance values to database on "/save" url
     * @param walletBalance A given body WalletBalance entity with all parameters
     */
    @PutMapping("/save")
    public WalletBalance saveWallet(@RequestBody @Valid WalletBalance walletBalance){
        return iWalletService.save(walletBalance);
    }
}
