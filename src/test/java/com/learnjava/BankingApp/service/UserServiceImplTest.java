package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.BalanceRequest;
import com.learnjava.BankingApp.dto.CreditDebitRequest;
import com.learnjava.BankingApp.dto.TransferRequest;
import com.learnjava.BankingApp.model.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.math.BigDecimal;
import java.util.Locale;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    private CreditDebitRequest creditDebitRequest;
    private BalanceRequest balanceRequest;
    private TransferRequest transferRequest;
    @BeforeTestClass
    public static void beforeClass(){
        System.out.println("This executes before any test cases");
    }
    @Before("deposit")
    void setUp(){
        creditDebitRequest = new CreditDebitRequest("1293838971",700.0);
        balanceRequest = new BalanceRequest("1234898723");
        transferRequest = new TransferRequest("2389712349",300.0,
                "manny","4329084736","GTBank");
        System.out.println("Running Tests...");
    }

    @Test
    void deposit() {
        ResponseEntity<String> deposit = userService.deposit(creditDebitRequest);
        assertTrue("Deposit failed", deposit.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void withdraw() {
        ResponseEntity<String> withdraw = userService.withdraw(creditDebitRequest);
        assertTrue("Withdrawal failed", withdraw.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void getAccountBalance() {
        ResponseEntity<BigDecimal> balance = userService.getAccountBalance(balanceRequest);
        assertTrue("Balance request failed", balance.getStatusCode().equals(HttpStatus.FOUND));
    }

    @Test
    void transfer() {
        ResponseEntity<String> transfer = userService.transfer(transferRequest);
        assertTrue("Transfer request failed", transfer.getStatusCode().equals(HttpStatus.OK));
    }
    @AfterTestClass
    public static void afterClass(){
        System.out.println("This executes after all test cases");
    }
}