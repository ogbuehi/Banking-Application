package com.learnjava.BankingApp.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private String operation;
    @NotBlank
    @Positive
    private BigDecimal amount;
    @Column(name = "transaction_time")
    private LocalDateTime time;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;
}
