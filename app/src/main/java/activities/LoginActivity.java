package activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import com.example.myapplication.R;

import Utilities.userDetailsManager;
import ViewModals.LoginViewModal;

public class LoginActivity extends AppCompatActivity {
    //    Setting the Hardcoded username and password for login
    private LoginViewModal viewModel;
    private static final String SUCSSESCODE= "201";
    private static final String WRONGCODE = "401";
    //    The views:
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initialize views that we gonna listen to:
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        viewModel = new ViewModelProvider(this).get(LoginViewModal.class);
        //handling the login result from server
        viewModel.getLoginResult().observe(this, loginMessage -> {
           if(loginMessage.equals(SUCSSESCODE)){
               String usernameInput = usernameEditText.getText().toString();
               userDetailsManager.getInstance().setUsername(usernameInput);
               Intent intent = new Intent(LoginActivity.this, MainActivity.class);
               startActivity(intent);

           }else if (loginMessage.equals(WRONGCODE)){
               AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
               builder.setTitle(R.string.invalid_details);
               builder.setMessage(R.string.the_username_or_password_is_incorrect_please_try_again);
               builder.setPositiveButton("OK", null);
               AlertDialog dialog = builder.create();
               dialog.show();
           }
           else{
               AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
               builder.setTitle(R.string.network_error);
               builder.setMessage(R.string.servers_issue_please_try_again_later);
               builder.setPositiveButton("OK", null);
               AlertDialog dialog = builder.create();
               dialog.show();

           }
        });
        //The view of the signup button (create account)
        Button createAccountButton = findViewById(R.id.createAccountButton);

        // Set onFocusChangeListener to clear hint text when EditText gains focus
        usernameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // Clear hint text when EditText gains focus
                usernameEditText.setHint("");
                usernameEditText.setTextColor(Color.WHITE);

            } else {
                // Restore hint text if EditText loses focus and is empty
                if (usernameEditText.getText().length() == 0) {
                    usernameEditText.setHint(R.string.enter_your_username);
                }
            }
        });
        //Set onFocusChangeListener to clear hint text when EditText gains focus:
        passwordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // Clear hint text when EditText gains focus
                passwordEditText.setHint("");
                passwordEditText.setTextColor(Color.WHITE);

            } else {
                // Restore hint text if EditText loses focus and is empty
                if (passwordEditText.getText().length() == 0) {
                    passwordEditText.setHint(R.string.enter_your_password);
                }
            }
        });
        //The onClickListener for the login button:
        loginButton.setOnClickListener(v -> {
            //Gets the username and password entered and converts them to Strings.
            String usernameInput = usernameEditText.getText().toString();
            String passwordInput = passwordEditText.getText().toString();
            viewModel.logIn(usernameInput, passwordInput);
    });
        createAccountButton.setOnClickListener(v -> {
            Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent2);
        });
    }
}