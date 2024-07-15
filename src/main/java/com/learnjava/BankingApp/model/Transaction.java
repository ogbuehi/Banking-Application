package com.learnjava.BankingApp.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Operation operation;
    private BigDecimal amount;
    @Column(name = "transaction_time")
    private LocalDateTime time;
}
