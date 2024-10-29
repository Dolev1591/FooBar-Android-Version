package validation_checks;

// BaseValidator is an abstract class implementing the Validator interface
public abstract class BaseValidator implements Validator {

    // Minimum and maximum length for validation
    public static final int MIN_LENGTH = 8;
    public static final int MAX_LENGTH = 25;

    /**
     * Checks if the input is empty.
     *
     * @param input The input string to be checked.
     * @return True if the input is empty, false otherwise.
     */
    @Override
    public boolean isEmpty(String input) {
        // Trim the input and check if it is empty
        return input.trim().isEmpty();
    }

    /**
     * Checks if the length of the input is within the valid range.
     *
     * @param input The input string to be checked.
     * @return True if the length is valid, false otherwise.
     */
    @Override
    public boolean isLengthValid(String input) {
        // Get the length of the input and check if it is within the valid range
        int inputLength = input.length();
        return inputLength >= MIN_LENGTH && inputLength <= MAX_LENGTH;
    }

    /**
     * Checks if the input contains only valid characters.
     *
     * @param input The input string to be checked.
     * @return True if the input contains valid characters, false otherwise.
     */
    @Override
    public boolean containsValidCharacters(String input) {
        // Regular expression allowing only letters (both upper and lower case) and numbers
        String validCharactersRegex = "^[a-zA-Z0-9]*$";
        return input.matches(validCharactersRegex);
    }
}
