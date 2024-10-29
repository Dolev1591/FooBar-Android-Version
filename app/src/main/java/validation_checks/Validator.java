package validation_checks;

import com.google.android.material.textfield.TextInputLayout;

/**
 * An interface defining methods for input validation.
 */
public interface Validator {

    /**
     * Validates the input and sets appropriate error messages in the associated TextInputLayout.
     *
     * @param input       The input string to be validated.
     * @param inputLayout The TextInputLayout associated with the input.
     */
    void isValid(String input, TextInputLayout inputLayout);

    /**
     * Checks if the input is empty.
     *
     * @param input The input string to be checked.
     * @return True if the input is empty, false otherwise.
     */
    boolean isEmpty(String input);

    /**
     * Checks if the length of the input is valid.
     *
     * @param input The input string to be checked.
     * @return True if the length is valid, false otherwise.
     */
    boolean isLengthValid(String input);

    /**
     * Checks if the input contains valid characters.
     *
     * @param input The input string to be checked.
     * @return True if the input contains valid characters, false otherwise.
     */
    boolean containsValidCharacters(String input);

}
