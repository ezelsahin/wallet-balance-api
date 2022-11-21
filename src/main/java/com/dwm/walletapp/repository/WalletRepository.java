package com.dwm.walletapp.repository;

import com.dwm.walletapp.entity.WalletBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository <WalletBalance, Integer> {

    WalletBalance findByCustomerId(int customerId);
}
