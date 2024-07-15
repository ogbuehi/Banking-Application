package com.learnjava.BankingApp.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String firstName;
    private String lastName;
    private String otherName;
    private String address;
    private String userName;
    private String contact;
    private String password;
    private String stateOfOrigin;
    private String email;
    private String gender;
}
