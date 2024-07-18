package com.learnjava.BankingApp.dto;

import com.learnjava.BankingApp.model.Beneficiary;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {
    private String accountNumber;
    private Double amount;
    private String beneficiaryName;
    private String beneficiaryAccountNumber;
    private String beneficiaryBankDetails;
}
