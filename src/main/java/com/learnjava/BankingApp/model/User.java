package com.learnjava.BankingApp.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "other_name")
    private String otherName;
    @NotBlank
    private String gender;
    @NotBlank
    private String address;
    @NotBlank
    @Column(name = "user_name")
    private String userName;
    @Column(name = "state_of_origin")
    private String stateOfOrigin;
    @NotBlank
    @Column(name = "email")
    private String email;
    @NotBlank
    private String contact;
    @NotBlank
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_account_id")
    private Account account;

}
