package com.learnjava.BankingApp.controller;

import com.learnjava.BankingApp.dto.AccountInfo;
import com.learnjava.BankingApp.dto.BankResponse;
import com.learnjava.BankingApp.dto.UserDto;
import com.learnjava.BankingApp.service.UserService;
import com.learnjava.BankingApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public BankResponse signUp(@RequestBody UserDto userDto) {
        return userService.createAccount(userDto);
    }
    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.FOUND)
    public BankResponse getBalance(@RequestBody AccountInfo accountInfo){
        return userService.getAccountBalance(accountInfo);
    }
}
