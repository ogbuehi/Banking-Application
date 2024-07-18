package com.learnjava.BankingApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Beneficiary {
    @Id
    @Column(name = "beneficiary_id")
    private Long beneficiaryId;
    private String name;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "bank_details")
    private String bankDetails;
}
