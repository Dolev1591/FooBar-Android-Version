package validation_checks;

import com.google.android.material.textfield.TextInputLayout;

// UsernameValidator is a class that extends BaseValidator and implements the Validator interface
public class UsernameValidator extends BaseValidator {

    /**
     * Validates the input string for a username.
     *
     * @param input       The input string to be validated.
     * @param inputLayout The TextInputLayout associated with the input field.
     */
    @Override
    public void isValid(String input, TextInputLayout inputLayout) {
        // Check if the input is empty
        if (isEmpty(input)) {
            inputLayout.setError("Required field: Use A-Z a-z 0-9");
        }
        // Check if the input contains invalid characters
        else if (!containsValidCharacters(input)) {
            inputLayout.setError("Invalid characters. Use a-z A-Z 0-9 only");
        }
        // Check if the input length is too short
        else if (!isLengthValid(input)) {
            inputLayout.setError("Username too short. Use at least 8 characters.");
        }
        // Input is valid, clear any existing errors
        else {
            inputLayout.setError(null);
        }
    }
}
