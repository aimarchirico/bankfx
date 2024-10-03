package bank.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;


/**
 * Represents a user in the bank system. A user has an SSN, name, password,
 * and a list of accounts.
 */
public class User {
    private final String ssn;
    private String name;
    private String password;
    private List<Account> accounts;

    /**
     * Constructs a new User with the given SSN, name, and password.
     *
     * @param ssn the user's social security number
     * @param name the user's name
     * @param password the user's password
     */
    public User(String ssn, String name, String password) {
        ssnCheck(ssn);
        passwordCheck(password);
        nameCheck(name);
        this.ssn = ssn;
        this.name = name;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    /**
     * Gets the user's social security number.
     *
     * @return the user's SSN
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Gets the user's name.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name the new name of the user
     * @throws IllegalArgumentException if the name is invalid
     */
    public void setName(String name) {
        nameCheck(name);
        this.name = name;
    }

    /**
     * Gets the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the new password of the user
     * @throws IllegalArgumentException if the password is invalid
     */
    public void setPassword(String password) {
        passwordCheck(password);
        this.password = password;
    }

    /**
     * Creates a new account for the user with the given account type.
     *
     * @param accountType the type of the new account
     */
    public void createAccount(String accountType) {
        Account account = new Account(0.0, this.getName(), accountType);
        addAccount(account);
    }

    /**
     * Adds an account to the user's list of accounts.
     *
     * @param account the account to add
     * @throws IllegalArgumentException if the account is null or already exists
     */
    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account can not be null");
        }
        if (accounts.contains(account)) {
            throw new IllegalArgumentException("Account already exists");
        }
        accounts.add(account);
    }

    /**
     * Deletes an account from the user's list of accounts.
     *
     * @param account the account to delete
     * @throws IllegalArgumentException if the user doesn't own the account
     */
    public void deleteAccount(Account account) {
        isOwnerOfAccountCheck(account);
        accounts.remove(account);
    }

    /**
     * Transfers money from one account to another.
     *
     * @param sourceAccount the account to withdraw from
     * @param targetAccount the account to deposit to
     * @param amount the amount to transfer
     * @param checkBothAccounts whether to check if the user owns both accounts
     * @throws IllegalArgumentException if the user doesn't own the source account
     *         or, if specified, the target account
     */
    public void transfer(Account sourceAccount, Account targetAccount, double amount, boolean checkBothAccounts) {
        isOwnerOfAccountCheck(sourceAccount);
        if (checkBothAccounts) {
            isOwnerOfAccountCheck(targetAccount);
        }
        sourceAccount.withDraw(amount);
        targetAccount.deposit(amount);
    }

    /**
     * Checks if the given password is valid.
     *
     * @param password the password to check
     * @throws IllegalArgumentException if the password is invalid
     */
    public void passwordCheck(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password can not be null");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password needs at least 6 charachters");
        }
        final String PASSORD_REGEX = "^(?=.*[a-zæøå])(?=.*[A-ZÆØÅ])(?=.*\\d).+$";
        Pattern pattern = Pattern.compile(PASSORD_REGEX);
        if (!pattern.matcher(password).matches()) {
            throw new IllegalArgumentException("Password needs at least 1 small letter, 1 capital letter and 1 number");
        }
    }

    /**
     * Validates the user's social security number.
     *
     * @param ssn the SSN to check
     * @throws IllegalArgumentException if the SSN is invalid
     */
    public void ssnCheck(String ssn) {
        if (ssn == null) {
            throw new IllegalArgumentException("Social security number can not be null");
        }
        if (ssn.length() != 11 || !ssn.matches("\\d+")) {
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

        try {
            LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalStateException("The first 6 numbers needs to be a date");
        }

    }

    /**
     * Validates the user's name.
     *
     * @param name the name to check
     * @throws IllegalArgumentException if the name is invalid
     */
    public void nameCheck(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name can not be null");
        }
        if (name.length() < 2) {
            throw new IllegalArgumentException("Name needs at least 2 characters");
        }
        final String NAME_REGEX = "^[a-zæøåÅA-ZÆØÅ\\s-]+$";
        if (!name.matches(NAME_REGEX)) {
            throw new IllegalArgumentException("Name can only contain letters, spaces, or hyphens");
        }
    }

    /**
     * Checks if the user owns the given account.
     *
     * @param account the account to check
     * @throws IllegalArgumentException if the user doesn't own the account
     */
    public void isOwnerOfAccountCheck(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account can not be null");
        }
        if (!accounts.contains(account)) {
            throw new IllegalArgumentException(
                    "You don't have access to this " + account.getAccountType() + " account.");
        }
    }

    /**
     * Compares this user's SSN with another user's SSN.
     *
     * @param o the object to compare with
     * @return true if the SSNs are equal, false otherwise
     */
    public boolean ssnEquals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(getSsn(), user.getSsn());
    }
}
