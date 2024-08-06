package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface UserService {
    ResponseEntity<String> createAccount(UserDto userDto);
    ResponseEntity<String> deposit(CreditDebitRequest creditDebitRequest);
    ResponseEntity<String> withdraw(CreditDebitRequest creditDebitRequest);
    ResponseEntity<BigDecimal> getAccountBalance(BalanceRequest balanceRequest);
    ResponseEntity<String> transfer(TransferRequest transferRequest);
}
