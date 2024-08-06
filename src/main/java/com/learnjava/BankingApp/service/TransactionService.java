package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.CreditDebitRequest;
import com.learnjava.BankingApp.dto.TransferRequest;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    ResponseEntity<String> deposit(CreditDebitRequest creditDebitRequest);
    ResponseEntity<String> withdraw(CreditDebitRequest creditDebitRequest);

    ResponseEntity<String> transfer(TransferRequest transferRequest);
    ResponseEntity<String> getTransactionHistory(Long id);
}
