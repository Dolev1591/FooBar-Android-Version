package activities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.imageview.ShapeableImageView;

import Utilities.UserPhotoHandler;
import ViewModals.AddPostVM;
import entities.Post;
import objects.User;

/**
 * this activity is responsible for the adding posts page
 */
public class AddPostActivity extends AppCompatActivity  {
    ImageView imageView;
    String Caption;
    Uri photoHolderUri;
    UserPhotoHandler photoHandler;
    AddPostVM addPostVM;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_layout);
        //attaching to the views
        ImageButton galleryPhoto = findViewById(R.id.Add_photo);
        imageView=findViewById(R.id.current_image_post);
        photoHandler = new UserPhotoHandler(imageView,this);
        TextView textView = (TextView) findViewById(R.id.caption_new_post);
        ImageButton cameraPhoto = findViewById(R.id.take_photo_button);
        ImageButton sumbitButton = findViewById(R.id.options_edit_photo);
        ShapeableImageView userLogo = findViewById(R.id.userLogoAddPost);
        TextView username = findViewById(R.id.user_name_edit_post);
        addPostVM = new ViewModelProvider(this).get(AddPostVM.class);
        //getting the user data
        addPostVM.getUserData().observe(this, user -> {
            if(!user.isSuccess()){
                Toast toast = Toast.makeText(AddPostActivity.this,
                        user.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            currentUser = user.getData();
            Bitmap userLogoBit = UserPhotoHandler.base64ToBitmap(user.getData().getProfilePic());
            userLogo.setImageBitmap(userLogoBit);
            username.setText(user.getData().getDisplayName());
        });
        // getting the result of the post addition
        addPostVM.getPostListData().observe(this, posts -> {
            if(!posts.isSuccess()){
                Toast toast = Toast.makeText(AddPostActivity.this,
                        posts.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }

        });
        addPostVM.getUserPosting();


        /**
         * letting the user attach photo from gallery
         */
        galleryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoHandler.checkPermissionAndOpenGallery();

            }
        });
        /**
         * letting the user pick a photo from his library
         */
        cameraPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoHandler.askCameraPermissions();

            }
        });
        //submitting the new photo
        sumbitButton.setOnClickListener(v -> {
            //extracting the caption and photo uri
            Caption = textView.getText().toString();
            String photoString;
            if(photoHolderUri== null){
                photoString =null;
            }else{
                photoString = UserPhotoHandler.uriToBase64(photoHolderUri,this);
            }
            Post post = new Post(currentUser.getUsername(), currentUser.getDisplayName(), null,currentUser.getProfilePic(),Caption,photoString);
            addPostVM.addPost(post);
            Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
    //handling the permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        photoHandler.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    //handling the result of the photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.photoHolderUri=photoHandler.onActivityResult(requestCode,resultCode,data);
    }







}