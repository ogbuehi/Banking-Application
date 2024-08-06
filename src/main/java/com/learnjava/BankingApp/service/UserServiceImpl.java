package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.LoginDto;
import com.learnjava.BankingApp.dto.UserDto;
import com.learnjava.BankingApp.error.BankAccountException;
import com.learnjava.BankingApp.model.Account;
import com.learnjava.BankingApp.model.EmailDetails;
import com.learnjava.BankingApp.model.User;
import com.learnjava.BankingApp.repository.UserRepository;
import com.learnjava.BankingApp.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

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

            User newUser = new User();
            newUser.setFirstName(userDto.getFirstName());
            newUser.setLastName(userDto.getLastName());
            newUser.setOtherName(userDto.getOtherName());
            newUser.setUserName(userDto.getUserName());
            newUser.setPassword(encoder.encode(userDto.getPassword()));
            newUser.setGender(userDto.getGender());
            newUser.setStateOfOrigin(userDto.getStateOfOrigin());
            newUser.setEmail(userDto.getEmail());
            newUser.setAddress(userDto.getAddress());
            newUser.setContact(userDto.getContact());
            newUser.setAccount(account);

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
            throw new BankAccountException("ERROR OCCURRED DURING ACCOUNT CREATION",e);
        }
    }
    @Override
    public ResponseEntity<String> verify(LoginDto loginDto) {
        try {
            Authentication auth =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
            if (auth.isAuthenticated()) {
                return new ResponseEntity<>(jwtService.generateToken(loginDto.getUserName()), HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>("Failed", HttpStatus.NOT_ACCEPTABLE);
        }catch (BankAccountException e){
            throw new BankAccountException("ERROR OCCURRED DURING USER VERIFICATION", e);
        }
    }
    @Override
    public ResponseEntity<String> getAllUsers() {
        try {
            userRepository.findAll();
            return new ResponseEntity<>("success",HttpStatus.FOUND);
        }catch (BankAccountException e){
            throw new BankAccountException("ERROR OCCURRED WHILE GETTING ALL USERS",e);
        }
    }

    @Override
    public ResponseEntity<String> getUser(Long id) {
        try {
            userRepository.findById(id);
            return new ResponseEntity<>("success",HttpStatus.FOUND);
        }catch (BankAccountException e){
            throw new BankAccountException("ERROR OCCURRED WHILE GETTING A USER",e);
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }catch (BankAccountException e){
            throw new BankAccountException("ERROR OCCURRED WHILE DELETING A USER",e);
        }
    }

    @Override
    public ResponseEntity<String> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }catch (BankAccountException e){
            throw new BankAccountException("ERROR OCCURRED WHILE DELETING ALL USERS",e);
        }
    }
}
