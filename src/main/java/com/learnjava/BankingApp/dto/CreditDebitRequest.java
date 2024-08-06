package com.learnjava.BankingApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditDebitRequest {
    @NotBlank
    private String accountNumber;
    @Positive
    @NotBlank
    private double amount;
}
