package com.learnjava.BankingApp.dto;

import com.learnjava.BankingApp.model.Beneficiary;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    @NotBlank
    private String accountNumber;
    @NotBlank
    @Positive
    private Double amount;
    private String beneficiaryName;
    @NotBlank
    private String beneficiaryAccountNumber;
    private String beneficiaryBankDetails;
}
