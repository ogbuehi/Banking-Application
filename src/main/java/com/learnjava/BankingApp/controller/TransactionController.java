package com.learnjava.BankingApp.controller;

import com.learnjava.BankingApp.dto.CreditDebitRequest;
import com.learnjava.BankingApp.dto.TransferRequest;
import com.learnjava.BankingApp.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PutMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody @Valid CreditDebitRequest creditDebitRequest){
        return transactionService.deposit(creditDebitRequest);
    }
    @PutMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody @Valid CreditDebitRequest creditDebitRequest){
        return transactionService.withdraw(creditDebitRequest);
    }
    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody @Valid TransferRequest transferRequest){
        return transactionService.transfer(transferRequest);
    }
    @GetMapping("/history")
    public ResponseEntity<String> transactionHistory(Long id){
        return transactionService.getTransactionHistory(id);
    }
}
