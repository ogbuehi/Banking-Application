package com.learnjava.BankingApp.controller;

import com.learnjava.BankingApp.dto.BalanceRequest;
import com.learnjava.BankingApp.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestBody @Valid BalanceRequest balanceRequest){
        return accountService.getAccountBalance(balanceRequest);
    }
    @GetMapping("/number")
    public ResponseEntity<String> getAccountNumber(@RequestBody @Valid BalanceRequest balanceRequest){
        return accountService.getAccountNumber(balanceRequest);
    }
}
