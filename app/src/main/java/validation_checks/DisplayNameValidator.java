package validation_checks;

import com.google.android.material.textfield.TextInputLayout;

// DisplayNameValidator is a class that extends BaseValidator and validates display names
public class DisplayNameValidator extends BaseValidator {
    public static final int MIN_DISPLAY_LENGTH = 2; // Minimum allowed length for a display name
    public static final int MAX_DISPLAY_LENGTH = 25; // Maximum allowed length for a display name

    /**
     * Validates the provided display name and sets an error message if invalid.
     *
     * @param input       The input display name to be validated.
     * @param inputLayout The TextInputLayout associated with the display name field.
     */
    @Override
    public void isValid(String input, TextInputLayout inputLayout) {
        if (isEmpty(input)) {
            inputLayout.setError("Required field");
        } else if (!containsValidCharacters(input)) {
            inputLayout.setError("Invalid characters. Use a-z A-Z 0-9 and 'space' only");
        } else if (!isLengthValid(input)) {
            inputLayout.setError("Display name too short. Use at least 2 characters.");
        } else {
            inputLayout.setError(null);
        }
    }

    /**
     * Checks if the length of the display name is within the valid range.
     *
     * @param input The input display name to be checked for length.
     * @return True if the length is within the valid range, false otherwise.
     */
    @Override
    public boolean isLengthValid(String input) {
        // Get the length of the display name and check if it is within the valid range
        int displayNameLength = input.length();
        return displayNameLength >= MIN_DISPLAY_LENGTH && displayNameLength <= MAX_DISPLAY_LENGTH;
    }

    /**
     * Checks if the display name contains only valid characters.
     *
     * @param input The input display name to be checked for valid characters.
     * @return True if the display name contains only valid characters, false otherwise.
     */
    @Override
    public boolean containsValidCharacters(String input) {
        // Regular expression allowing only letters (both upper and lower case),
        // numbers, and spaces
        String regex = "^[a-zA-Z0-9 ]*$";
        return input.matches(regex);
    }
}
