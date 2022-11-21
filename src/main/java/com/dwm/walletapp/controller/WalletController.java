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
     * @return Returns a customers current wallet balance with given customerId  on "/getBalance" url
     */
    @GetMapping("/getBalance/{customerId}")
    public BigDecimal getBalance(@PathVariable @Min(1) int customerId) {
        return iWalletService.getWalletBalance(customerId);
    }

    /**
     * Tries to complete a withdrawal transaction demand from a customer wallet with given body on "/withdrawalRequest" url
     * @param withdrawalTransactionDTO  A given body of withdrawal transaction dto with all parameters
     * @return Returns customers id and new balance after withdrawal
     */
    @PutMapping ("/withdrawalRequest")
    public WalletBalance newWithdrawalRequest(@RequestBody @Valid WithdrawalTransactionDTO withdrawalTransactionDTO) {
        return iWalletService.withdrawalTransaction(withdrawalTransactionDTO);
    }

    /**
     * Tries to complete a deposit transaction request from a customer wallet with given body on "/depositRequest" url
     * @param depositTransactionDTO  A given body of deposit transaction dto with all parameters
     * @return Returns customers id and new balance after withdrawal
     */
    @PutMapping("/depositRequest")
    public WalletBalance newDepositRequest(@RequestBody @Valid DepositTransactionDTO depositTransactionDTO) {
        return iWalletService.depositTransaction(depositTransactionDTO);
    }

    /**
     * Tries to find a customers transactions with customer id which is given on "/getTransactionHistory/{customerId}" url
     * @param customerId  A unique numeric value which belongs to a specific customer
     * @return Returns a customers all transactions as a list according to given customer id
     */
    @GetMapping("/getTransactionHistory/{customerId}")
    public List<Transaction> getTransactions(@PathVariable @Min(1) int customerId){
        return iWalletService.getTransactionHistory(customerId);
    }

    /**
     * Adds new customer ID and wallet balance values to database on "/save" url
     * @param walletBalance A given wallet balance entity body with all parameters
     * @return Returns saved customers wallet balance and customer id information
     */
    @PutMapping("/save")
    public WalletBalance saveWallet(@RequestBody @Valid WalletBalance walletBalance){
        return iWalletService.save(walletBalance);
    }
}
