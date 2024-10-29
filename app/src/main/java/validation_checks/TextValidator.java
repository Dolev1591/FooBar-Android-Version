package validation_checks;

import android.text.Editable;
import android.text.TextWatcher;
import com.google.android.material.textfield.TextInputLayout;

// TextValidator is a class that implements the TextWatcher interface for validating text input
public class TextValidator implements TextWatcher {
    private final TextInputLayout layout;
    private final Validator validator;

    /**
     * Constructor for TextValidator.
     *
     * @param textLayout The TextInputLayout associated with the text input field.
     * @param validator  The Validator instance responsible for validating the input.
     */
    public TextValidator(TextInputLayout textLayout, Validator validator) {
        this.layout = textLayout;
        this.validator = validator;
    }

    /**
     * Called to notify you that somewhere within `s`, the text has been changed.
     *
     * @param s      The updated text.
     * @param start  The starting point of the changed part in the text.
     * @param before The length of the text that has been replaced.
     * @param count  The length of the new text.
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Invoke the Validator to check and handle the validity of the input
        validator.isValid(s.toString(), layout);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Not used in this implementation
    }
    @Override
    public void afterTextChanged(Editable s) {
        // Not used in this implementation
    }
}
