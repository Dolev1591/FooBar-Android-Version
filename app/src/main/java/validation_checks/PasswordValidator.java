package validation_checks;

import com.google.android.material.textfield.TextInputLayout;

// PasswordValidator is a class that implements validation logic for passwords
public class PasswordValidator extends BaseValidator {

    /**
     * Validates the given password and updates the associated TextInputLayout with error messages
     * if the password is invalid.
     *
     * @param input       The password to be validated.
     * @param inputLayout The TextInputLayout associated with the password input field.
     */
    @Override
    public void isValid(String input, TextInputLayout inputLayout) {
        if (isEmpty(input)) {
            inputLayout.setError("Required field: Must use uppercase, lowercase letters and numbers");
        } else if (!containsValidCharacters(input)) {
            inputLayout.setError("Invalid characters. Use a-z A-Z 0-9 only");
        } else if (!isLengthValid(input)) {
            inputLayout.setError("Password too short. Use at least 8 characters.");
        } else if (!checkComplexity(input)) {
            inputLayout.setError("Not complex enough. Use uppercase, lowercase letters and numbers ");
        } else {
            inputLayout.setError(null);
        }
    }

    /**
     * Checks the complexity of the password, ensuring it contains at least one uppercase letter,
     * one lowercase letter, and one digit.
     *
     * @param password The password to check for complexity.
     * @return True if the password meets complexity requirements, false otherwise.
     */
    public boolean checkComplexity(String password) {
        // Check if the password meets complexity requirements
        return containsUppercase(password) &&
                containsLowercase(password) &&
                containsDigit(password);
    }

    /**
     * Checks if the password contains at least one uppercase letter.
     *
     * @param password The password to check.
     * @return True if the password contains at least one uppercase letter, false otherwise.
     */
    private boolean containsUppercase(String password) {
        return !password.equals(password.toLowerCase());
    }

    /**
     * Checks if the password contains at least one lowercase letter.
     *
     * @param password The password to check.
     * @return True if the password contains at least one lowercase letter, false otherwise.
     */
    private boolean containsLowercase(String password) {
        return !password.equals(password.toUpperCase());
    }

    /**
     * Checks if the password contains at least one digit.
     *
     * @param password The password to check.
     * @return True if the password contains at least one digit, false otherwise.
     */
    private boolean containsDigit(String password) {
        return password.matches(".*\\d.*");
    }
}
