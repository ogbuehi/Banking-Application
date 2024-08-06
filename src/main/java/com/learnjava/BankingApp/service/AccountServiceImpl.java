package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.BalanceRequest;
import com.learnjava.BankingApp.error.BankAccountException;
import com.learnjava.BankingApp.model.Account;
import com.learnjava.BankingApp.repository.AccountRepository;
import com.learnjava.BankingApp.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepo;
    @Override
    public ResponseEntity<BigDecimal> getAccountBalance(BalanceRequest balanceRequest) {
        try {
            boolean exists = accountRepo.existsById(balanceRequest.getId());
            if (!exists) {
                return new ResponseEntity<>(BigDecimal.valueOf(-1), HttpStatus.BAD_REQUEST);
            }
            Account account = accountRepo.findByAccountNumber(balanceRequest.getAccountNumber());
            BigDecimal balance = account.getBalance();
            return new ResponseEntity<>(balance, HttpStatus.FOUND);
        }catch (BankAccountException e){
            throw new BankAccountException("ERROR OCCURRED WHILE GETTING ACCOUNT BALANCE",e);
        }
    }

    @Override
    public ResponseEntity<String> getAccountNumber(BalanceRequest balanceRequest) {
        try {
            boolean exists = accountRepo.existsById(balanceRequest.getId());
            if (!exists) {
                return new ResponseEntity<>(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE, HttpStatus.BAD_REQUEST);
            }
            Account account = accountRepo.findByAccountNumber(balanceRequest.getAccountNumber());
            String accountNumber = account.getAccountNumber();
            return new ResponseEntity<>(accountNumber, HttpStatus.FOUND);
        }catch (BankAccountException e){
            throw new BankAccountException("ERROR OCCURRED WHILE GETTING ACCOUNT NUMBER",e);
        }
    }
}
