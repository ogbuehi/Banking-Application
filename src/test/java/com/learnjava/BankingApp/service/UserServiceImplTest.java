package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.BalanceRequest;
import com.learnjava.BankingApp.dto.CreditDebitRequest;
import com.learnjava.BankingApp.dto.TransferRequest;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.math.BigDecimal;

import static org.springframework.test.util.AssertionErrors.assertTrue;
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private TransactionServiceImpl transactionService;
    @Autowired
    private BalanceRequest balanceRequest;
    @Autowired
    private CreditDebitRequest creditDebitRequest;
    @Autowired
    private TransferRequest transferRequest;
    @BeforeTestClass
    public static void beforeClass(){
        System.out.println("This executes before any test cases");
    }
    @Before("deposit")
    void setUp(){
        creditDebitRequest = new CreditDebitRequest("1293838971",700.0);
        transferRequest = new TransferRequest("2389712349",300.0,
                "manny","4329084736","GTBank");
        balanceRequest = new BalanceRequest(2L,"2398789762");
        System.out.println("Running Tests...");
    }

    @Test
    void deposit() {
        ResponseEntity<String> deposit = transactionService.deposit(creditDebitRequest);
        assertTrue("Deposit failed", deposit.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void withdraw() {
        ResponseEntity<String> withdraw = transactionService.withdraw(creditDebitRequest);
        assertTrue("Withdrawal failed", withdraw.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void getAccountBalance() {
        ResponseEntity<BigDecimal> balance = accountService.getAccountBalance(balanceRequest);
        assertTrue("Balance request failed", balance.getStatusCode().equals(HttpStatus.FOUND));
    }

    @Test
    void transfer() {
        ResponseEntity<String> transfer = transactionService.transfer(transferRequest);
        assertTrue("Transfer request failed", transfer.getStatusCode().equals(HttpStatus.OK));
    }
    @AfterTestClass
    public static void afterClass(){
        System.out.println("This executes after all test cases");
    }
}