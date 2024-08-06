package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface UserService {
    ResponseEntity<String> createAccount(UserDto userDto);
    ResponseEntity<String> verify(LoginDto userDto);
    ResponseEntity<String> getAllUsers();
    ResponseEntity<String> getUser(Long id);
    ResponseEntity<String> deleteUser(Long id);
    ResponseEntity<String> deleteAllUsers();

}
