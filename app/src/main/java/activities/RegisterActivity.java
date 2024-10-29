package activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Toast;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.io.IOException;
import java.util.Objects;

import Utilities.UserPhotoHandler;
import ViewModals.RegistrationViewModal;
import validation_checks.DisplayNameValidator;
import validation_checks.PasswordTextWatcher;
import validation_checks.PasswordValidator;
import validation_checks.TextValidator;
import validation_checks.UsernameValidator;
import validation_checks.Validator;

public class RegisterActivity extends AppCompatActivity {
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int PHOTOWIDTH = 400;
    public static final int PHOTOHEIGHT = 400;
    public static final int PHOTOQ = 70;
    public static final String SUCCESS = "201";
    public static final String FAILURE = "401";
    public static final int GALLERY_REQUEST_CODE = 2;
    public static final int CAMERA_PERMISSION_CODE = 3;
    private RegistrationViewModal registrationViewModal;
    private ImageView userPhoto;
    private Bitmap bitmapPhoto;
    private Uri uriphoto;
    private boolean isFirstRun = true;
    private TextInputLayout usernameLayout;
    private TextInputLayout displayNameLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText passwordEditText;
    private TextInputLayout confirmLayout;
    private TextInputEditText usernameEditText;
    private TextInputEditText displayNameEditText;
    private TextInputEditText confirmEditText;
    private boolean hasChosenPicture = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize UI components
        usernameLayout = findViewById(R.id.addUserName);
        usernameEditText = findViewById(R.id.usernameEditText);
        displayNameLayout = findViewById(R.id.displayName);
        displayNameEditText = findViewById(R.id.displayNameEditText);
        passwordLayout = findViewById(R.id.addPassword);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmLayout = findViewById(R.id.confirmPassword);
        confirmEditText = findViewById(R.id.confirmEditText);
        Button btnExists = findViewById(R.id.btnExists);
        Button submitBtn = findViewById(R.id.submitBtn);
        userPhoto = findViewById(R.id.imageViewUserPhoto);
        Button takePhotoBtn = findViewById(R.id.takePhotoBtn);
        Button choosePhotoBtn = findViewById(R.id.choosePhotoBtn);
        //initialize the view model
        registrationViewModal = new ViewModelProvider(this).get(RegistrationViewModal.class);
        // Create instances of validators
        Validator usernameValidator = new UsernameValidator();
        Validator passwordValidator = new PasswordValidator();
        Validator displayNameValidator = new DisplayNameValidator();

        // Set initial error messages for required fields
        usernameLayout.setError("Required field: Use A-Z a-z 0-9");
        displayNameLayout.setError("Required field");
        passwordLayout.setError("Required field: Must use uppercase, lowercase letters and numbers");
        confirmLayout.setError("Required field: Must match password field");

        // Attach a TextWatcher to the usernameEditText with the specified validator
        usernameEditText.addTextChangedListener(new TextValidator(usernameLayout, usernameValidator));
        // Attach a TextWatcher to the passwordEditText with the specified validator
        passwordEditText.addTextChangedListener(new TextValidator(passwordLayout, passwordValidator));
        // Attach a PasswordTextWatcher to the confirmEditText with the specified validators
        confirmEditText.addTextChangedListener(new PasswordTextWatcher(passwordLayout, confirmLayout));
        // Attach a TextWatcher to the displayNameEditText with the specified validator
        displayNameEditText.addTextChangedListener(new TextValidator(displayNameLayout,
                displayNameValidator ));
        //handling the registration result from server
        registrationViewModal.getRegistrationMessage().observe(this, registrationMessage -> {

            if(registrationMessage.equals(SUCCESS)){
      
                Toast toast = Toast.makeText(RegisterActivity.this,
                       "Welcome to FooBar!", Toast.LENGTH_LONG);
                toast.show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);


            }else if(registrationMessage.equals(FAILURE)){
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("User aleady exists,try again")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();

            } else{
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage(registrationMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        });
        // Password visibility toggle setup
        if (isFirstRun) {
            // Set inputType to text (visible password) for the first run
            passwordEditText.setInputType(
                    android.text.InputType.TYPE_CLASS_TEXT |
                            android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            confirmEditText.setInputType(
                    android.text.InputType.TYPE_CLASS_TEXT |
                            android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            // Set inputType to password (hidden password) for subsequent runs
            passwordEditText.setInputType(
                    android.text.InputType.TYPE_CLASS_TEXT |
                            android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            confirmEditText.setInputType(
                    android.text.InputType.TYPE_CLASS_TEXT |
                            android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }

        // Set up the password toggle for password field
        passwordLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        passwordLayout.setEndIconTintList(ContextCompat.getColorStateList(this,
                R.color.teal_700));
        passwordLayout.setEndIconOnClickListener(v -> {
            // Toggle password visibility
            int currentInputType = passwordEditText.getInputType();
            if (currentInputType ==
                    (android.text.InputType.TYPE_CLASS_TEXT |
                            android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                passwordEditText.setInputType(
                        android.text.InputType.TYPE_CLASS_TEXT |
                                android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                passwordEditText.setInputType(
                        android.text.InputType.TYPE_CLASS_TEXT |
                                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });



        // Set up the password toggle for confirm password field
        confirmLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        confirmLayout.setEndIconTintList(ContextCompat.getColorStateList(this,
                R.color.teal_700));
        confirmLayout.setEndIconOnClickListener(v -> {
            // Toggle password visibility
            int currentInputType = confirmEditText.getInputType();
            if (currentInputType ==
                    (android.text.InputType.TYPE_CLASS_TEXT |
                            android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                confirmEditText.setInputType(
                        android.text.InputType.TYPE_CLASS_TEXT |
                                android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                confirmEditText.setInputType(
                        android.text.InputType.TYPE_CLASS_TEXT |
                                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        // 'I already have an account' button handler
        btnExists.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Submit Button
        submitBtn.setOnClickListener(v -> {
            if (!hasErrors()) {
                // Check if the user has chosen a picture and show a message accordingly
                if (!hasChosenPicture) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Picture not chosen");
                    builder.setMessage("You did not choose a profile picture. Default picture is set.");
                    builder.setPositiveButton("OK", (dialog, which) -> {
                        // Continue with the registration process after the user presses the "OK" button.
                        continueRegistration();
                    });
                    builder.show();

                } else {
                    continueRegistration();
                }
            } else {
                // If one or more of the fields have errors, show an 'alert' message
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Invalid Details");
                builder.setMessage("One or more of the fields invalid. " +
                        "Please fill all the fields correctly.");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // Take photo and Choose photo buttons functionality
        takePhotoBtn.setOnClickListener(v -> askCameraPermission());
        choosePhotoBtn.setOnClickListener(v -> dispatchPickImageIntent());
        isFirstRun = false; // Set isFirstRun to false after the first run
    }

    private void continueRegistration() {
        String username = usernameEditText.getText().toString();
        String displayName = displayNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String profilePic;
        if(bitmapPhoto!=null){
            bitmapPhoto = UserPhotoHandler.resizeBitmap(bitmapPhoto,PHOTOWIDTH,PHOTOHEIGHT);
            bitmapPhoto = UserPhotoHandler.compressBitmap(bitmapPhoto,PHOTOQ);
            profilePic = UserPhotoHandler.bitmapToBase64(bitmapPhoto);
        }else if(uriphoto!=null){
            Bitmap photoConvert = UserPhotoHandler.compressBitmap
                    (UserPhotoHandler.uriToBitmap(this, uriphoto),PHOTOQ);
            profilePic =UserPhotoHandler.bitmapToBase64(photoConvert);
            }
        else{
            profilePic ="";
        }

        registrationViewModal.register(username, displayName, password, profilePic);

//        // Switch to login screen
//        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//        startActivity(intent);
    }

    /**
     * Checks if there are validation errors in any of the input fields.
     * @return True if there are errors, false otherwise.
     */
    private boolean hasErrors() {
        return usernameLayout.getError() != null ||
                displayNameLayout.getError() != null ||
                passwordLayout.getError() != null ||
                confirmLayout.getError() != null;
    }

    /**
     * ActivityResultLauncher for picking an image from the gallery.
     * Handles the result of the image selection and sets the selected image to the userPhoto ImageView.
     */
    private final ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    try {
                        // Attempt to retrieve the selected image as a bitmap from the given URI
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                RegisterActivity.this.getContentResolver(), uri);
                        // Set the selected bitmap to the userPhoto ImageView
                        userPhoto.setImageBitmap(bitmap);
                        bitmapPhoto= bitmap;
                        hasChosenPicture = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    /**
     * Initiates the process of picking an image from the gallery.
     * Launches the pickImageLauncher ActivityResultLauncher with the specified MIME type.
     */
    private void dispatchPickImageIntent() {
        pickImageLauncher.launch("image/*");
    }

    /**
     * Checks if the app has the necessary camera permission.
     * If the permission is not granted, requests it from the user.
     * If the permission is already granted, proceeds to take a photo.
     */
    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
        } else {
            takePhoto();
        }
    }

    /**
     * Handles the result of the permission request for the camera.
     *
     * @param requestCode  The request code passed when requesting permissions.
     * @param permissions  The requested permissions.
     * @param grantResults The results of the permission request.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();

            } else {
                // Display a toast message indicating that camera permission is required
                Toast.makeText(this, "Camera permission is required to use camera.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    // ActivityResultLauncher for capturing an image from the camera
    private final ActivityResultLauncher<Intent> takePictureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Process the captured image from the camera
                    assert result.getData() != null;
                    Bundle extras = result.getData().getExtras();
                    if (extras != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        this.bitmapPhoto = imageBitmap;
                        userPhoto.setImageBitmap(imageBitmap);
                        hasChosenPicture = true;
                    }
                }
            }
    );

    // Method to initiate the process of capturing a photo using the device's camera
    private void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureLauncher.launch(cameraIntent);
    }


    /**
     * Handles the result of activities started with startActivityForResult.
     * It processes the captured image from the camera or the selected image from the gallery.
     *
     * @param requestCode The request code passed to startActivityForResult.
     * @param resultCode  The result code returned by the child activity.
     * @param data        The data intent containing the result data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap image = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                this.bitmapPhoto = image;
                userPhoto.setImageBitmap(image);
            }
            else if (requestCode == GALLERY_REQUEST_CODE) {
                // Handle picked image from gallery
                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        try {
                            this.uriphoto=selectedImageUri;
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(), selectedImageUri);
                            userPhoto.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
