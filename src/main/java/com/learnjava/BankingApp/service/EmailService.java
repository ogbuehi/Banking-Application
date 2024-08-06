package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.model.EmailDetails;

public interface EmailService {
    String sendEmail(EmailDetails emailDetails);
}
