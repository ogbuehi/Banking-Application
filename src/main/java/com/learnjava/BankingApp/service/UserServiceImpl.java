package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.*;
import com.learnjava.BankingApp.model.*;
import com.learnjava.BankingApp.repository.UserRepository;
import com.learnjava.BankingApp.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.hibernate.validator.internal.util.Contracts.assertValueNotNull;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public BankResponse createAccount(UserDto userDto) {
        /*
        Check if user already exists.If not create an account
         */
        if (userRepository.existsByEmail(userDto.getEmail())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_RESPONSE_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_RESPONSE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        Account account = Account.builder()
                .accountNumber(AccountUtils.generateAccountNumber())
                .balance(BigDecimal.ZERO)
                .build();
        User newUser = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .otherName(userDto.getOtherName())
                .userName(userDto.getUserName())
                .gender(userDto.getGender())
                .password(userDto.getPassword())
                .stateOfOrigin(userDto.getStateOfOrigin())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .contact(userDto.getContact())
                .account(account)
                .build();
        // Create an encoder with strength 16
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode(newUser.getPassword());
        newUser.setPassword(result);
        User savedUser =  userRepository.save(newUser);
        assertTrue(encoder.matches(newUser.getPassword(),result),"authentication successful!!");
         EmailDetails emailDetails = EmailDetails.builder()
                 .recipient(newUser.getEmail())
                 .subject("ACCOUNT CREATION")
                 .msgBody("Account was Successfully Created!!.\n Your Account Details are:" +
                         "\n Account Name: " +savedUser.getFirstName()+" "+savedUser.getLastName()
                         +" "+savedUser.getOtherName()+"\n" +
                         " Account Number: "+savedUser.getAccount().getAccountNumber())
                 .build();
         emailService.sendEmail(emailDetails);
         return BankResponse.builder()
                 .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_CODE)
                 .responseMessage(AccountUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                 .accountInfo(AccountInfo.builder()
                         .accountName(savedUser.getUserName())
                         .accountNumber(savedUser.getAccount().getAccountNumber())
                         .accountBalance(savedUser.getAccount().getBalance())
                         .build())
                 .build();
    }

    @Override
    public BankResponse deposit(CreditDebitRequest creditDebitRequest) {
        Boolean exists = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
        if (!exists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());
        Transaction transaction = Transaction.builder()
                .time(LocalDateTime.now())
                .amount(BigDecimal.valueOf(creditDebitRequest.getAmount()))
                .operation(Operation.DEPOSIT)
                .build();
        BigDecimal current = transaction.getAmount().add(foundUser.getAccount().getBalance());
        foundUser.getAccount().setBalance(current);
        userRepository.save(foundUser);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_EXISTS_RESPONSE_CODE)
                .responseMessage(AccountUtils.ACCOUNT_EXISTS_RESPONSE_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName("Account Name: "+foundUser.getFirstName()+" "+foundUser.getLastName()
                                +" "+foundUser.getOtherName())
                        .accountNumber(foundUser.getAccount().getAccountNumber())
                        .accountBalance(foundUser.getAccount().getBalance())
                        .build())
                .build();
    }

    @Override
    public BankResponse withdraw(CreditDebitRequest creditDebitRequest) {
        Boolean exists = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
        if (!exists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());
       if (foundUser.getAccount().getBalance().intValue() < 500){
            return BankResponse.builder()
                    .responseMessage(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .responseCode(AccountUtils.INSUFFICIENT_FUNDS_CODE)
                    .accountInfo(null)
                    .build();
        }
        Transaction transaction = Transaction.builder()
                .time(LocalDateTime.now())
                .amount(BigDecimal.valueOf(creditDebitRequest.getAmount()))
                .operation(Operation.DEPOSIT)
                .build();
        BigDecimal current = transaction.getAmount().subtract(foundUser.getAccount().getBalance());
        foundUser.getAccount().setBalance(current);
        userRepository.save(foundUser);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_EXISTS_RESPONSE_CODE)
                .responseMessage(AccountUtils.ACCOUNT_EXISTS_RESPONSE_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName("Account Name: "+foundUser.getFirstName()+" "+foundUser.getLastName()
                                +" "+foundUser.getOtherName())
                        .accountNumber(foundUser.getAccount().getAccountNumber())
                        .accountBalance(foundUser.getAccount().getBalance())
                        .build())
                .build();

    }

    @Override
    public BankResponse getAccountBalance(AccountInfo accountInfo) {
        Boolean exists = userRepository.existsByAccountNumber(accountInfo.getAccountNumber());
        if (!exists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(accountInfo.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_EXISTS_RESPONSE_CODE)
                .responseMessage(AccountUtils.ACCOUNT_EXISTS_RESPONSE_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName("Account Name: "+foundUser.getFirstName()+" "+foundUser.getLastName()
                        +" "+foundUser.getOtherName())
                        .accountNumber(foundUser.getAccount().getAccountNumber())
                        .accountBalance(foundUser.getAccount().getBalance())
                        .build())
                .build();
    }

    @Override
    public BankResponse transfer(TransferRequest transferRequest) {
        Boolean exists = userRepository.existsByAccountNumber(transferRequest.getAccountNumber());
        if (!exists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(transferRequest.getAccountNumber());
        if (foundUser.getAccount().getBalance().intValue() < 500){
            return BankResponse.builder()
                    .responseMessage(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .responseCode(AccountUtils.INSUFFICIENT_FUNDS_CODE)
                    .accountInfo(null)
                    .build();
        }
         Beneficiary beneficiary = Beneficiary.builder()
                .name(transferRequest.getBeneficiaryName())
                .accountNumber(transferRequest.getBeneficiaryAccountNumber())
                .bankDetails(transferRequest.getBeneficiaryBankDetails())
                .build();
        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(transferRequest.getAmount()))
                .operation(Operation.TRANSFER)
                .beneficiary(beneficiary)
                .build();
        BigDecimal current = transaction.getAmount().subtract(foundUser.getAccount().getBalance());
        foundUser.getAccount().setBalance(current);
        userRepository.save(foundUser);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_EXISTS_RESPONSE_CODE)
                .responseMessage(AccountUtils.ACCOUNT_EXISTS_RESPONSE_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName("Account Name: "+foundUser.getFirstName()+" "+foundUser.getLastName()
                                +" "+foundUser.getOtherName())
                        .accountNumber(foundUser.getAccount().getAccountNumber())
                        .accountBalance(foundUser.getAccount().getBalance())
                        .build())
                .build();
    }

    @Override
    public TransactionResponse transactionHistory(CreditDebitRequest creditDebitRequest) {
        Boolean exists = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
        if (!exists){
            BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();

             return TransactionResponse.builder()
                     .amount(null)
                     .time(null)
                     .operation(null)
                     .build();
        }
        User foundUser = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());

        return TransactionResponse.builder()
                .operation(foundUser.getAccount().getTransaction().getOperation())
                .time(foundUser.getAccount().getTransaction().getTime())
                .amount(foundUser.getAccount().getTransaction().getAmount())
                .build();
    }

}
