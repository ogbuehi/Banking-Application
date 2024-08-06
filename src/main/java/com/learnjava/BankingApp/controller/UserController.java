package com.learnjava.BankingApp.controller;

import com.learnjava.BankingApp.dto.LoginDto;
import com.learnjava.BankingApp.dto.UserDto;
import com.learnjava.BankingApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody @Valid UserDto userDto) {
        return userService.createAccount(userDto);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        return userService.verify(loginDto);
    }
    @GetMapping("/get-all")
    public ResponseEntity<String> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllUsers() {
        return userService.deleteAllUsers();
    }

}
