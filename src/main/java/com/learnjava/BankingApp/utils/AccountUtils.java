package com.learnjava.BankingApp.utils;

import java.time.Year;

public class AccountUtils {
    public static final String ACCOUNT_EXISTS_RESPONSE_CODE = "001";
    public static final String ACCOUNT_EXISTS_RESPONSE_MESSAGE = "Account already Created!!";
    public static final String ACCOUNT_CREATION_SUCCESS_CODE = "002";
    public static final String ACCOUNT_CREATION_SUCCESS_MESSAGE = "Account Successfully Created!!";
    public static final String ACCOUNT_DOES_EXIST_CODE = "003";
    public static final String ACCOUNT_DOES_EXIST_MESSAGE = "Account does not exist";
    public static String generateAccountNumber(){
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;
        int randomNumber = (int) Math.floor(Math.random()*(max - min + 1) + min);
        String year = String.valueOf(currentYear);
        String number = String.valueOf(randomNumber);
        return year + number;
    }
    public static boolean verifyEmail(String email){
        return true;
    }

}
