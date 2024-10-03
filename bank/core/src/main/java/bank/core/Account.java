package bank.core;

import java.util.Collections;
import java.util.List;

public class Account {
    private Double balance;
    private String name;
    private String accountType;
    private final List<String> accountTypes = Collections.unmodifiableList(List.of("Sparekonto", "Brukskonto"));

    /**
     * Constructs a new `Account` object.
     *
     * @param balance     The initial balance of the account. Must be greater than
     *                    or equal to zero.
     * @param name        The name of the account holder.
     * @param accountType The type of account. Must be one of the allowed account
     *                    types
     * @throws IllegalArgumentException if the balance is negative or the account
     *                                  type is invalid.
     */
    public Account(Double balance, String name, String accountType) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance can't be less than 0, balance: " + balance);
        }
        accountTypeCheck(accountType);
        nameCheck(name);
        this.balance = balance;
        this.name = name;
        this.accountType = accountType;
    }

    /**
     * @return String name of the user
     */

    public String getName() {
        return this.name;
    }

    /**
     * @return String account type
     */

     public String getAccountType() {
        return this.accountType;
    }

    /**
     * Return the balance of the account
     * 
     * @return double
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Deposits an amount to the account
     * 
     * @param amount The amount to deposit. Must be greater than or equal to zero
     * @throws IllegalArgumentException if the amount is negative
     */
    public void deposit(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Can't deposit negative amount, amount : " + amount);
        }
        this.balance += amount;
    }

    /**
     * Withdraws an amount from the account
     * 
     * @param amount The amount to withdraw. Must be greater than or equal to zero
     * @throws IllegalArgumentException if the amount is negative
     */
    public void withDraw(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Can't withdraw negative amount, amount : " + amount);
        }
        if (getBalance() - amount < 0) {
            throw new IllegalArgumentException(
                    "Can't withdraw an amount that makes the balance negative: balance after withdrawl: "
                            + (getBalance() - amount));
        }
        this.balance -= amount;
    }

    /**
     * Changes the name of the account
     * 
     * @param name
     * @throws IllegalArgumentException if the name is not valid
     */
    public void changeName(String name) {
        nameCheck(name);
        this.name = name;
    }

    /**
     * Changes account type
     * 
     * @param accountType
     * @throws IllegalArgumentException if teh accountType is invalid
     */
    public void changeAccountType(String accountType) {
        accountTypeCheck(accountType);
        this.accountType = accountType;
    }

    /**
     * Validates the provided account type to ensure it is one of the allowed types.
     *
     * @param accountType The account type to be validated.
     * @throws IllegalArgumentException if the account type is not valid.
     */
    private void accountTypeCheck(String accountType) {
        if (!accountTypes.contains(accountType)) {
            throw new IllegalArgumentException("Account must be of the valid types, accountType: " + accountType);
        }
    }

    /**
     * Validates the provided name to ensure it meets the following criteria:
     * <ul>
     * <li>Not null</li>
     * <li>At least 2 characters long</li>
     * <li>Contains only letters, spaces, and hyphens</li>
     * </ul>
     *
     * @param name The name to be validated.
     * @throws IllegalArgumentException if the name does not meet the validation
     *                                  criteria.
     */
    private void nameCheck(String name) {
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
}
