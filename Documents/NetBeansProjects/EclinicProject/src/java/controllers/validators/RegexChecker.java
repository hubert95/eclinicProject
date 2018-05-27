/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hubert Januszek
 */
public abstract class RegexChecker {
    final String lettersPattern = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ\\s]*$";
    final String withoutSpecialCharsPattern = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9.\\s]*$";
    final String emailPattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    final String postalCodePLPattern = "^[0-9]{2}-[0-9]{3}$";
    final String houseNumberPattern = "^[0-9A-Za-z]";
    final String phoneNumberPattern = "^[1-9]{1}[0-9]{2}-[0-9]{3}-[0-9]{3}$";
    final String phoneNumberHomePattern = "^[1-9]{2}-[1-9]{1}[0-9]{2}-[0-9]{2}-[0-9]{2}$";
    final String passwordPattern = "^(?=.*[0-9])(?=.*[a-zżźćńółęąś])(?=.*[A-ZŻŹĆĄŚĘŁÓŃ])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    final String peselPattern = "^\\d{11}$";
    
    public boolean check(String pattern, String checkString){
        Pattern checkRegex;
        checkRegex = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

        Matcher regexMatcher = checkRegex.matcher(checkString);

        return regexMatcher.find();
    }
}
