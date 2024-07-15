package com.learnjava.BankingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditDebitRequest {
    private String accountNumber;
    private Double amount;
}
