package com.learnjava.BankingApp.repository;

import com.learnjava.BankingApp.model.Account;
import com.learnjava.BankingApp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Boolean existsByAccount(Account account);
}
