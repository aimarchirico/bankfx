package bank.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class User {
    private final String ssn;
    private String name;
    private String password;

    public User(String ssn, String name, String password) {
        ssnCheck(ssn);
        passwordCheck(password);
        nameCheck(name);
        this.ssn = ssn;
        this.name = name;
        this.password = password;
    }

    public String getSsn() {
        return ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        nameCheck(name);
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        passwordCheck(password);
        this.password = password;
    }

    public void passwordCheck(String password){
        if (password == null){
            throw new IllegalArgumentException("Password can not be null");
        }
        if(password.length() < 6){
            throw new IllegalArgumentException("Password needs at least 6 charachters");
        }
        final String PASSORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        Pattern pattern = Pattern.compile(PASSORD_REGEX);
        if(!pattern.matcher(password).matches()){
            throw new IllegalArgumentException("Password needs at least 1 small letter, 1 capital letter and 1 number");
        }
    }

    public void ssnCheck(String ssn){
        if (ssn == null){
            throw new IllegalArgumentException("Social security number can not be null");
        }
        if(ssn.length() != 11 || !ssn.matches("\\d+")){
            throw new IllegalArgumentException("Social security number has to be 11 numbers");
        }
        int day = Integer.parseInt(ssn.substring(0, 2));
        int month = Integer.parseInt(ssn.substring(2, 4));
        int year = Integer.parseInt(ssn.substring(4, 6));

        // Konverter to-sifret år til fire-sifret år
        year += (year >= 24 ? 1900 : 2000);

        // Opprett en streng i format ddMMyyyy for å bruke med LocalDate
        String dateStr = String.format("%02d%02d%04d", day, month, year);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        try{
            LocalDate date = LocalDate.parse(dateStr, formatter);
        }
        catch(DateTimeParseException e){
            throw new IllegalStateException("The first 6 numbers needs to be a date");
        }
        
    }

    public void nameCheck(String name){
        if(name == null){
            throw new IllegalArgumentException("Name can not be null");
        }
        if (name.length() < 2){
            throw new IllegalArgumentException("Name needs at least 2 characters");
        }
        final String NAME_REGEX = "^[a-zA-Z\\s-]+$";
        if (!name.matches(NAME_REGEX)) {
            throw new IllegalArgumentException("Name can only contain letters, spaces, or hyphens");
        }
    }
}
