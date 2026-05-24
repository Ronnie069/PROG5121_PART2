package com.chatapp;

import java.util.regex.Pattern;

/**
 * Login class handles user registration and authentication.
 */
public class Login {

    private final String username;
    private final String password;
    private final String phoneNumber;
    private final String firstName;
    private final String lastName;

    /**
     * Constructor for Login
     *
     * @param username    the user's chosen username
     * @param password    the user's password
     * @param firstName   the user's first name
     * @param lastName    the user's last name
     * @param phoneNumber the user's international phone number
     */
    public Login(String username, String password, String firstName,
                 String lastName, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Checks if username is correctly formatted.
     * Requirements: Must contain at least one underscore (_) and length <= 5
     *
     * @return true if username meets the criteria
     */
    public boolean checkUserName() {
        return username != null &&
                username.contains("_") &&
                username.length() <= 5;
    }

    /**
     * Checks if password meets complexity requirements.
     * Requirements:
     * - At least 8 characters
     * - At least one uppercase letter
     * - At least one digit
     * - At least one special character
     *
     * @return true if password is complex enough
     */
    public boolean checkPasswordComplexity() {
        if (password == null) return false;

        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
        return Pattern.compile(regex).matcher(password).matches();
    }

    /**
     * Checks if phone number is in a valid international format.
     * Accepts formats like: +27 82 123 4567, +27821234567, 0821234567 (SA example)
     * You can adjust the regex based on your specific requirements.
     *
     * @return true if phone number appears valid
     */
    public boolean checkCellPhoneNumber() {
        if (phoneNumber == null) return false;

        // International phone number regex (allows +, digits, and spaces)
        String regex = "^\\+[0-9]{9,12}$";
        return Pattern.compile(regex).matcher(phoneNumber.replaceAll("\\s+", "")).matches();
    }

    /**
     * Registers the user by validating all fields.
     *
     * @return Success message or specific error message
     */
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "Username successfully captured.\n" +
                "Password successfully captured.\n" +
                "Cell phone number successfully added.";
    }

    /**
     * Attempts to log in the user.
     *
     * @param enteredUser username entered by user
     * @param enteredPass password entered by user
     * @return true if credentials match
     */
    public boolean loginUser(String enteredUser, String enteredPass) {
        if (enteredUser == null || enteredPass == null) {
            return false;
        }
        return this.username.equals(enteredUser) && this.password.equals(enteredPass);
    }

    /**
     * Returns appropriate login status message.
     *
     * @param isLoggedIn whether login was successful
     * @return welcome message or error message
     */

    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    // Getters
    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}