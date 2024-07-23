package com.learnjava.BankingApp.utils;

import java.time.Year;

public class AccountUtils {
    public static final String ACCOUNT_EXISTS_RESPONSE_CODE = "001";
    public static final String ACCOUNT_EXISTS_RESPONSE_MESSAGE = "Account already Created!!";
    public static final String ACCOUNT_CREATION_SUCCESS_CODE = "002";
    public static final String ACCOUNT_CREATION_SUCCESS_MESSAGE = "Account Successfully Created!!";
    public static final String ACCOUNT_DOES_EXIST_CODE = "003";
    public static final String ACCOUNT_DOES_EXIST_MESSAGE = "Account does not exist";
    public static final String INSUFFICIENT_FUNDS_MESSAGE = "Insufficient funds for this transaction!!";
    public static final String INSUFFICIENT_FUNDS_CODE = "004";
    public static String generateAccountNumber(){
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;
        int randomNumber = (int) Math.floor(Math.random()*(max - min + 1) + min);
        String year = String.valueOf(currentYear);
        String number = String.valueOf(randomNumber);
        return year + number;
    }
    // public static boolean verifyEmail(String email){
    //     return true;
    // }
    public static String generatePassword(String password){
        // generate a string of upper case letters having length 2  
        String upperCaseStr = RandomStringUtils.random(2, 65, 90, true, true);  
         
        // generate a string of lower case letters having length 2  
        String lowerCaseStr = RandomStringUtils.random(2, 97, 122, true, true);  
          
        // generate a string of numeric letters having length 2  
        String numbersStr = RandomStringUtils.randomNumeric(2);  
          
        // generate a string of special chars having length 2  
        String specialCharStr = RandomStringUtils.random(2, 33, 47, false, false);  
          
        // generate a string of alphanumeric letters having length 2  
        String totalCharsStr = RandomStringUtils.randomAlphanumeric(2);  
          
        // concatenate all the strings into a single one  
        String demoPassword = upperCaseStr.concat(lowerCaseStr)  
          .concat(numbersStr)  
          .concat(specialCharStr)  
          .concat(totalCharsStr);  
          
        // create a list of Char that stores all the characters, numbers and special characters   
        List<Character> listOfChar = demoPassword.chars()  
                .mapToObj(data -> (char) data)  
                .collect(Collectors.toList());  
          
        // use shuffle() method of the Collections to shuffle the list elements   
        Collections.shuffle(listOfChar);  
          
        //generate a random string(secure password) by using list stream() method and collect() method  
        String password = listOfChar.stream()  
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)  
                .toString();  
                  
        // return RandomStringGenerator password to the main() method   
        return password;  
    }

}
