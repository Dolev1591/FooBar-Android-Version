package activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.HashMap;

import Utilities.UserPhotoHandler;
import ViewModals.EditPostVM;
import ViewModals.EditProfileVM;
import objects.User;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView profilePic;
    private EditText displayname;
    private ImageButton takePhoto;
    private ImageButton galleryPhoto;
    private ImageButton menuButton;

    private Uri photoHolderUri;
    User user;
    UserPhotoHandler photoHandler;
    EditProfileVM editProfileVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //attaching to the views
        profilePic = findViewById(R.id.edit_profile_picture);
        displayname = findViewById(R.id.display_name_box);
        takePhoto = findViewById(R.id.take_photo_button_editProfile);
        galleryPhoto = findViewById(R.id.galleryPhoto_editProfile);
        photoHandler = new UserPhotoHandler(profilePic, this);
        menuButton =findViewById(R.id.editUserMenu);
        editProfileVM = new ViewModelProvider(this).get(EditProfileVM.class);
        //getting the user data
        editProfileVM.getUserData().observe(this, ApiUser -> {
            if(ApiUser!=null) {
                if (ApiUser.getMessage().equals("User fetched successfully")) {
                    user = ApiUser.getData();
                    displayname.setText(user.getDisplayName());
                    profilePic.setImageBitmap(UserPhotoHandler.base64ToBitmap(user.getProfilePic()));
                }else if (ApiUser.getMessage().equals("User updated successfully")){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                else if (ApiUser.getMessage().equals("User deleted successfully")){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast toast = Toast.makeText(EditProfileActivity.this,
                            ApiUser.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                }
            }
        });
        editProfileVM.getUserPosting();

        //setting the listeners
        galleryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoHandler.checkPermissionAndOpenGallery();

            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoHandler.askCameraPermissions();

            }
        });
        //handling the action by the user
        menuButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.edit_profile_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.set_changes_user) {
                    //setting the new string
                    String newDisplayName = displayname.getText().toString();
                    String newPhoto;
                    if(photoHolderUri== null){
                        newPhoto =user.getProfilePic();
                    }else{
                        newPhoto = UserPhotoHandler.uriToBase64(photoHolderUri,EditProfileActivity.this);
                    }
                    HashMap<String,String> changes =new HashMap<String,String>();
                    changes.put("displayName",newDisplayName);
                    changes.put("profilePic",newPhoto);
                    editProfileVM.editUser(user,changes);
                    return true;
                } else if (id == R.id.delete_user) {
                    editProfileVM.deleteUser(user);
                    return true;
                }
                else {
                    return false;
                }
            });

            // Show the popup menu
            popup.show();

        });

    }


    //handling the results of the photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.photoHolderUri=photoHandler.onActivityResult(requestCode,resultCode,data);
    }
    //handling the permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        photoHandler.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }






}