package com.learnjava.BankingApp.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

@Entity
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "beneficiary")
public class Beneficiary {
    @Id
    @Column(name = "beneficiary_id")
    private Long beneficiaryId;
    private String name;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "bank_details")
    private String bankDetails;
    @OneToOne(mappedBy = "beneficiary",
    cascade = CascadeType.ALL)
    private Transaction transaction;
}
