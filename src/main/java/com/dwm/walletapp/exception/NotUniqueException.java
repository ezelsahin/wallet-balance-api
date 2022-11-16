package com.dwm.walletapp.exception;

public class NotUniqueException extends RuntimeException{
    public NotUniqueException(String message) {super(message + " is already exist!");}
}
