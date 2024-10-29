package validation_checks;

import android.text.Editable;
import android.text.TextWatcher;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

// PasswordTextWatcher is a class that implements TextWatcher to monitor changes in password fields
public class PasswordTextWatcher implements TextWatcher {
    private final TextInputLayout passwordLayout; // TextInputLayout for the password field
    private final TextInputLayout confirmLayout; // TextInputLayout for the confirm password field

    /**
     * Constructor for PasswordTextWatcher.
     *
     * @param passwordLayout The TextInputLayout associated with the password field.
     * @param confirmLayout  The TextInputLayout associated with the confirm password field.
     */
    public PasswordTextWatcher(TextInputLayout passwordLayout, TextInputLayout confirmLayout) {
        this.passwordLayout = passwordLayout;
        this.confirmLayout = confirmLayout;
    }

    /**
     * Called to notify you that somewhere within 'editable' the text has been changed.
     *
     * @param charSequence The characters in 'editable' after the change.
     * @param i            The position of the beginning of the changed part in the 'editable' sequence.
     * @param i1           The length of the changed part in the 'editable' sequence.
     * @param i2           The length of the new sequence.
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Not needed for this example
    }

    /**
     * Called to notify you that somewhere within 'editable' the text has been changed.
     *
     * @param charSequence The characters in 'editable' after the change.
     * @param i            The position of the beginning of the changed part in the 'editable' sequence.
     * @param i1           The length of the changed part in the 'editable' sequence.
     * @param i2           The length of the new sequence.
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Not needed for this example
    }

    /**
     * Called to notify you that somewhere within 'editable' the text has been changed.
     *
     * @param editable The text that has been changed.
     */
    @Override
    public void afterTextChanged(Editable editable) {
        String password = Objects.requireNonNull(passwordLayout.getEditText()).getText().toString();
        String confirmPassword = Objects.requireNonNull(confirmLayout.getEditText()).getText().toString();

        if (confirmPassword.isEmpty()) {
            confirmLayout.setError("Required field: Must match password field");
        } else if (!password.equals(confirmPassword)) {
            confirmLayout.setError("Does not match password");
        } else {
            confirmLayout.setError(null);
        }
    }
}
