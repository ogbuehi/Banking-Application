package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.CreditDebitRequest;
import com.learnjava.BankingApp.dto.TransferRequest;
import com.learnjava.BankingApp.error.BankAccountException;
import com.learnjava.BankingApp.model.Account;
import com.learnjava.BankingApp.model.Beneficiary;
import com.learnjava.BankingApp.model.Operation;
import com.learnjava.BankingApp.model.Transaction;
import com.learnjava.BankingApp.repository.TransactionRepository;
import com.learnjava.BankingApp.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<String> deposit(CreditDebitRequest creditDebitRequest) {
        Account account = new Account();
        try {
            boolean exists = transactionRepository.existsByAccount(account);
            if (!exists) {
                return new ResponseEntity<>(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE, HttpStatus.BAD_REQUEST);
            }
            Transaction transaction = new Transaction();
            transaction.setTime(LocalDateTime.now());
            transaction.setAmount(BigDecimal.valueOf(creditDebitRequest.getAmount()));
            transaction.setOperation(Operation.DEPOSIT.name().toUpperCase());

            BigDecimal newBalance = account.getBalance().add(BigDecimal.valueOf(creditDebitRequest.getAmount()));
            account.setBalance(newBalance);
            return new ResponseEntity<>("DEPOSIT SUCCESSFUL!!", HttpStatus.OK);
        }catch (BankAccountException e){
            throw new BankAccountException("ERROR OCCURRED DURING DEPOSIT", e);
        }
    }

    @Override
    public ResponseEntity<String> withdraw(CreditDebitRequest creditDebitRequest) {
        Account account = new Account();
        try {
            boolean exists = transactionRepository.existsByAccount(account);
            if (!exists) {
                return new ResponseEntity<>(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE, HttpStatus.BAD_REQUEST);
            }
            // Check if user account balance is less than #0.0 after the withdrawal.
            // If it is, the user is not eligible to withdraw.
            // Return insufficient funds message.
            if (account.getBalance().doubleValue() - creditDebitRequest.getAmount() < 0.0) {
                return new ResponseEntity<>(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE, HttpStatus.NOT_ACCEPTABLE);
            }
            Transaction transaction = new Transaction();
            transaction.setTime(LocalDateTime.now());
            transaction.setAmount(BigDecimal.valueOf(creditDebitRequest.getAmount()));
            transaction.setOperation(Operation.WITHDRAW.name().toUpperCase());

            BigDecimal newBalance = account.getBalance().subtract(BigDecimal.valueOf(creditDebitRequest.getAmount()));
            account.setBalance(newBalance);
            return new ResponseEntity<>("WITHDRAWAL SUCCESSFUL", HttpStatus.OK);
        }catch (BankAccountException e){
            throw new BankAccountException("ERROR OCCURRED DURING WITHDRAWAL", e);
        }
    }

    @Override
    public ResponseEntity<String> transfer(TransferRequest transferRequest) {
        Account account = new Account();
        try {
            boolean exists = transactionRepository.existsByAccount(account);
            if (!exists) {
                return new ResponseEntity<>(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE, HttpStatus.BAD_REQUEST);
            }
            // Check if user account balance is less than #0.0 after transfer is done.
            // If it is, the user is not eligible to transfer.
            // Return insufficient funds message.
            if (account.getBalance().doubleValue() - transferRequest.getAmount() < 0.0) {
                return new ResponseEntity<>(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE, HttpStatus.NOT_ACCEPTABLE);
            }
            Beneficiary beneficiary = new Beneficiary();
            beneficiary.setName(transferRequest.getBeneficiaryName());
            beneficiary.setAccountNumber(transferRequest.getBeneficiaryAccountNumber());
            beneficiary.setBankDetails(transferRequest.getBeneficiaryBankDetails());

            Transaction transaction = new Transaction();
            transaction.setTime(LocalDateTime.now());
            transaction.setAmount(BigDecimal.valueOf(transferRequest.getAmount()));
            transaction.setOperation(Operation.TRANSFER.name().toUpperCase());

            BigDecimal newBalance = account.getBalance().subtract(BigDecimal.valueOf(transferRequest.getAmount()));
            account.setBalance(newBalance);

            return new ResponseEntity<>("TRANSFER SUCCESSFUL", HttpStatus.OK);
        }catch (BankAccountException e) {
            throw new BankAccountException("ERROR OCCURRED DURING TRANSFER", e);
        }
    }

    @Override
    public ResponseEntity<String> getTransactionHistory(Long id) {
        try {
            transactionRepository.findAllById(Collections.singleton(id));
            return new ResponseEntity<>("success",HttpStatus.FOUND);
        }catch (BankAccountException e){
            throw new BankAccountException("ERROR OCCURRED WHILE GETTING TRANSACTION HISTORY",e);
        }
    }
}
