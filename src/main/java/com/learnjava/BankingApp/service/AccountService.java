package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.BalanceRequest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface AccountService {
    ResponseEntity<BigDecimal> getAccountBalance(BalanceRequest balanceRequest);
    ResponseEntity<String> getAccountNumber(BalanceRequest balanceRequest);

}
