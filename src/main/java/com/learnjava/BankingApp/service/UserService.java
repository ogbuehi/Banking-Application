package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.*;

import java.math.BigDecimal;

public interface UserService {
    BankResponse createAccount(UserDto userDto);
    BankResponse deposit(CreditDebitRequest creditDebitRequest);
    BankResponse withdraw(CreditDebitRequest creditDebitRequest);
    BankResponse getAccountBalance(AccountInfo accountInfo);
    BankResponse transfer(CreditDebitRequest creditDebitRequest);
    TransactionResponse transactionHistory(CreditDebitRequest creditDebitRequest);
}
