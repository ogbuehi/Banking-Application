package com.learnjava.BankingApp.dto;

import com.learnjava.BankingApp.model.Operation;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionResponse {
    private Operation operation;
    private BigDecimal amount;
    private LocalDateTime time;
}
