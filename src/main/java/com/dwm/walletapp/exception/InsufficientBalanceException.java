package com.dwm.walletapp.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message + " is not enough!");
    }
}
