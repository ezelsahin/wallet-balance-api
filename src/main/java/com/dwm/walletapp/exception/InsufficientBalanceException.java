package com.dwm.walletapp.exception;

import javax.naming.InsufficientResourcesException;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message + " is not enough!");
    }
}
