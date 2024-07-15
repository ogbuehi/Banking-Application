package com.learnjava.BankingApp.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "account_balance")
    private BigDecimal balance;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "creation_time")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "last_modified_time")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_transaction_id")
    private Transaction transaction;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;
}
