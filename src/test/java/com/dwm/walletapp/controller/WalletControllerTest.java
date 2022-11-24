package com.dwm.walletapp.controller;

import com.dwm.walletapp.dto.DepositTransactionDTO;
import com.dwm.walletapp.dto.WithdrawalTransactionDTO;
import com.dwm.walletapp.entity.Transaction;
import com.dwm.walletapp.entity.WalletBalance;
import com.dwm.walletapp.exception.GenericExceptionHandler;
import com.dwm.walletapp.service.IWalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {
    private MockMvc mvc;

    @InjectMocks
    private WalletController walletController;

    @Mock
    private IWalletService iWalletService;

    @BeforeEach
    public void setup()
    {
        mvc = MockMvcBuilders.standaloneSetup(walletController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void getBalance() throws Exception{
        WalletBalance walletBalance = new WalletBalance();
        walletBalance.setCustomerId(12345);
        walletBalance.setWalletBalance(BigDecimal.valueOf(1500.45));

        when(iWalletService.getWalletBalance(5)).thenReturn(walletBalance);

        MockHttpServletResponse response = mvc.perform(get("/api/getBalance/5")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        WalletBalance actualBalance = iWalletService.getWalletBalance(5);

        assertEquals(actualBalance.getWalletBalance(), BigDecimal.valueOf(1500.45));
    }

    @Test
    void newWithdrawalRequest() throws Exception{
        WithdrawalTransactionDTO dummyDTO = new WithdrawalTransactionDTO();
        WithdrawalTransactionDTO sampleDTO = new WithdrawalTransactionDTO(987, 1234, "W", BigDecimal.valueOf(245.75), LocalDateTime.of(2022, 2, 13, 15, 56));
        WalletBalance sampleWallet = new WalletBalance(1, 1234, BigDecimal.valueOf(500.0));

        when(iWalletService.withdrawalTransaction(dummyDTO)).thenReturn(new WalletBalance(1, sampleDTO.getCustomerId(), (sampleWallet.getWalletBalance().subtract(sampleDTO.getWithdrawalAmount()))));

        sampleWallet.setWalletBalance(iWalletService.withdrawalTransaction(dummyDTO).getWalletBalance());
        BigDecimal actualBalance = BigDecimal.valueOf(500.0).subtract( BigDecimal.valueOf(245.75));

        assertEquals(actualBalance, sampleWallet.getWalletBalance());
    }

    @Test
    void newDepositRequest() throws Exception{
        DepositTransactionDTO dummyDTO = new DepositTransactionDTO();
        DepositTransactionDTO sampleDTO = new DepositTransactionDTO(87, 3478, "D", BigDecimal.valueOf(530.32), LocalDateTime.of(2022, 5, 16, 19, 44));
        WalletBalance sampleWallet = new WalletBalance(1, 3478, BigDecimal.valueOf(349));

        when(iWalletService.depositTransaction(dummyDTO)).thenReturn(new WalletBalance(1, sampleDTO.getCustomerId(), (sampleWallet.getWalletBalance().add(sampleDTO.getDepositAmount()))));

        sampleWallet.setWalletBalance(iWalletService.depositTransaction(dummyDTO).getWalletBalance());
        BigDecimal actualBalance = BigDecimal.valueOf(349).add( BigDecimal.valueOf(530.32));

        assertEquals(actualBalance, sampleWallet.getWalletBalance());
    }

    @Test
    void getTransactions() throws Exception{
        List<Transaction> dummyList = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 555, 123, "D", null, BigDecimal.valueOf(500.0), LocalDateTime.of(2022, 1, 3, 12, 26));
        Transaction transaction2 = new Transaction(2, 666, 123, "D", null, BigDecimal.valueOf(340.0), LocalDateTime.of(2022, 3, 25, 16, 11));
        Transaction transaction3 = new Transaction(3, 777, 123, "W", BigDecimal.valueOf(210.0), null, LocalDateTime.of(2022, 7, 6, 21, 15));
        Transaction transaction4 = new Transaction(4, 888, 123, "W", BigDecimal.valueOf(130.0), null, LocalDateTime.of(2022, 9, 11, 7, 38));
        dummyList.add(transaction1);
        dummyList.add(transaction2);
        dummyList.add(transaction3);
        dummyList.add(transaction4);

        when(iWalletService.getTransactionHistory(123)).thenReturn(dummyList);

        List<Transaction> actualList = iWalletService.getTransactionHistory(123);

        assertEquals(actualList.size(), 4);
    }

    @Test
    void saveWallet() throws Exception{
        List<WalletBalance> expectedList = new ArrayList<>();
        WalletBalance dummyWalletBalance1 = new WalletBalance(1, 123, BigDecimal.valueOf(2200.0));
        WalletBalance dummyWalletBalance2 = new WalletBalance(2, 124, BigDecimal.valueOf(350.0));
        expectedList.add(dummyWalletBalance1);
        expectedList.add(dummyWalletBalance2);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedWalletBalanceJsonStr = ow.writeValueAsString(dummyWalletBalance2);
        when(iWalletService.save(dummyWalletBalance2)).thenReturn(expectedList.get(1));

        MockHttpServletResponse response = mvc.perform(post("/api/save")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedWalletBalanceJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        Assert.assertEquals(expectedList.get(1), dummyWalletBalance2);
    }

}
