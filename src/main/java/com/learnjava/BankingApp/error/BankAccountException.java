package com.learnjava.BankingApp.error;

public class BankAccountException extends RuntimeException{
    public BankAccountException(String message, BankAccountException e){
        super(message,e);
    }
}
