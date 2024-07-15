package com.learnjava.BankingApp.service;

import com.learnjava.BankingApp.dto.EmailDetails;

public interface EmailService {
    String sendEmail(EmailDetails emailDetails);
}
