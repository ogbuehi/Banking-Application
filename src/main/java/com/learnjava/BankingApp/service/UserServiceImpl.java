package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.*;
import com.learnjava.BankingApp.error.BankAccountException;
import com.learnjava.BankingApp.model.*;
import com.learnjava.BankingApp.repository.UserRepository;
import com.learnjava.BankingApp.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.hibernate.validator.internal.util.Contracts.assertValueNotNull;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public ResponseEntity<String> createAccount(UserDto userDto) {
        try {

        /*
        Check if user already exists.If not create an account
         */
            if (userRepository.existsByEmail(userDto.getEmail())) {
                return new ResponseEntity<>(
                        AccountUtils.ACCOUNT_EXISTS_RESPONSE_MESSAGE, HttpStatus.BAD_REQUEST);

            }
            Account account = new Account();
            account.setAccountNumber(AccountUtils.generateAccountNumber());
            account.setBalance(BigDecimal.ZERO);
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
            assert (encoder.matches(newUser.getPassword(), result));
            newUser.setPassword(result);
            User savedUser = userRepository.save(newUser);
            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(newUser.getEmail())
                    .subject("ACCOUNT CREATION")
                    .msgBody("Account was Successfully Created!!.\n Your Account Details are:" +
                            "\n Account Name: " + savedUser.getFirstName() + " " + savedUser.getLastName()
                            + " " + savedUser.getOtherName() + "\n" +
                            " Account Number: " + savedUser.getAccount().getAccountNumber())
                    .build();
            emailService.sendEmail(emailDetails);
            return new ResponseEntity<>("success",HttpStatus.CREATED);
        }catch (BankAccountException e){
            e.accountException("ERROR OCCURRED DURING ACCOUNT CREATION");
        }
         return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> deposit(CreditDebitRequest creditDebitRequest) {
        try {

            Boolean exists = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
            if (!exists) {
                return new ResponseEntity<>(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE, HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>("DEPOSIT SUCCESSFUL!!", HttpStatus.OK);
        }catch (BankAccountException e){
            e.accountException("ERROR OCCURRED DURING DEPOSIT");
        }
        return new ResponseEntity<>("DEPOSIT FAILED", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> withdraw(CreditDebitRequest creditDebitRequest) {
        try {
            Boolean exists = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
            if (!exists) {
                return new ResponseEntity<>(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE, HttpStatus.BAD_REQUEST);
            }
            User foundUser = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());
            // Check if user account balance is less than #500.
            // If it is, the user is not eligible to withdraw.
            // Return insufficient funds message.
            if (foundUser.getAccount().getBalance().doubleValue() < 500.0) {
                return new ResponseEntity<>(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE, HttpStatus.NOT_ACCEPTABLE);
            }
            Transaction transaction = Transaction.builder()
                    .time(LocalDateTime.now())
                    .amount(BigDecimal.valueOf(creditDebitRequest.getAmount()))
                    .operation(Operation.DEPOSIT)
                    .build();
            BigDecimal current = transaction.getAmount().subtract(foundUser.getAccount().getBalance());
            foundUser.getAccount().setBalance(current);
            userRepository.save(foundUser);
            return new ResponseEntity<>("WITHDRAWAL SUCCESSFUL", HttpStatus.OK);
        }catch (BankAccountException e){
            e.accountException("ERROR OCCURRED DURING WITHDRAWAL");
        }
        return new ResponseEntity<>("WITHDRAWAL FAILED", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<BigDecimal> getAccountBalance(BalanceRequest balanceRequest) {

        try {
            Boolean exists = userRepository.existsByAccountNumber(balanceRequest.getAccountNumber());
            if (!exists) {
                return new ResponseEntity<>(BigDecimal.valueOf(-1), HttpStatus.BAD_REQUEST);
            }
            User foundUser = userRepository.findByAccountNumber(balanceRequest.getAccountNumber());
            BigDecimal balance = foundUser.getAccount().getBalance();
            return new ResponseEntity<>(balance, HttpStatus.FOUND);
        }catch (BankAccountException e){
            e.accountException("ERROR OCCURRED WHILE GETTING ACCOUNT BALANCE");
        }
       return new ResponseEntity<>(BigDecimal.valueOf(-1), HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public ResponseEntity<String> transfer(TransferRequest transferRequest) {
        try {
            Boolean exists = userRepository.existsByAccountNumber(transferRequest.getAccountNumber());
            if (!exists) {
                return new ResponseEntity<>(AccountUtils.ACCOUNT_DOES_EXIST_MESSAGE, HttpStatus.BAD_REQUEST);
            }
            User foundUser = userRepository.findByAccountNumber(transferRequest.getAccountNumber());
            // Check if user account balance is less than #500.
            // If it is, the user is not eligible to transfer.
            // Return insufficient funds message.
            if (foundUser.getAccount().getBalance().doubleValue() < 500.0) {
                return new ResponseEntity<>(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE, HttpStatus.NOT_ACCEPTABLE);
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
            return new ResponseEntity<>("TRANSFER SUCCESSFUL", HttpStatus.OK);
        }catch (BankAccountException e){
            e.accountException("ERROR OCCURRED DURING TRANSFER");
        }
        return new ResponseEntity<>("TRANSFER FAILED", HttpStatus.BAD_REQUEST);
    }
}
