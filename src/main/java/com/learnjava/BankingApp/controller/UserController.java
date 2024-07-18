package com.learnjava.BankingApp.controller;

import com.learnjava.BankingApp.dto.*;
import com.learnjava.BankingApp.service.UserService;
import com.learnjava.BankingApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
        return userService.createAccount(userDto);
    }
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestBody BalanceRequest balanceRequest){
        return userService.getAccountBalance(balanceRequest);
    }
    @PutMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.deposit(creditDebitRequest);
    }
    @PutMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.withdraw(creditDebitRequest);
    }
    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest transferRequest){
        return userService.transfer(transferRequest);
    }
}
