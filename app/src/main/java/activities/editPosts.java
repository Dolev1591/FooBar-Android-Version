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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.HashMap;

import Utilities.PostManager;
import ViewModals.EditPostVM;
import entities.Post;
import objects.User;
import Utilities.UserPhotoHandler;

//edit post class
public class editPosts extends AppCompatActivity {
    Post currentPost;

    ImageView imageView;
    User user;
    TextView username;

    Uri photoHolderUri;
    TextView currentCaption;
    TextView userName;
    ShapeableImageView  userLogo;
    ImageButton optionsButton;
    ImageButton cameraPhoto;
    ImageButton galleryPhoto;
    HashMap<String,String> changes;


    EditPostVM editPostsVM;

    UserPhotoHandler photoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_posts);
        imageView = findViewById(R.id.current_image_post);
        optionsButton = findViewById(R.id.options_edit_photo);
        cameraPhoto = findViewById(R.id.take_photo_button);
        galleryPhoto = findViewById(R.id.Add_photo);
        photoHandler = new UserPhotoHandler(imageView, this);
        userName = findViewById(R.id.user_name_edit_post);
        userLogo = findViewById(R.id.edit_post_logo);
        ImageButton cameraPhoto = findViewById(R.id.take_photo_button);
        currentCaption = findViewById(R.id.caption_new_post);
        currentPost = PostManager.getInstance().getPost();
        setCurrentPost();
        changes = new HashMap<>();
        editPostsVM = new ViewModelProvider(this).get(EditPostVM.class);
        //getting the user edit result
        editPostsVM.getPostListData().observe(this, apiResponse -> {
            if(apiResponse.getMessage().equals("Delete succeed!") ||
                    apiResponse.getMessage().equals("Cant preform task")||
                    apiResponse.getMessage().equals("Network Error")||
                    apiResponse.getMessage().equals("Edit succeed!")||
                    apiResponse.getMessage().equals("Black Listed url! try again")){
                //if the task is done
                if (apiResponse.isSuccess()){
                    Toast toast = Toast.makeText(editPosts.this,
                            editPostsVM.getPostListData().getValue().getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    if(apiResponse.getMessage().equals("Edit succeed!")){
                        setChangeCurrentPost();
                    }
                    //go back to the feed activity
                    Intent intent1 = new Intent(editPosts.this, MainActivity.class);
                    startActivity(intent1);
                }else {
                    //if the task is failed
                     Toast toast = Toast.makeText(editPosts.this,
                            editPostsVM.getPostListData().getValue().getMessage(), Toast.LENGTH_LONG);
                    toast.show();

                    Intent intent1 = new Intent(editPosts.this, MainActivity.class);
                    startActivity(intent1);

                }
            }

        });

        //handling the actions chosen by the user
        optionsButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.edit_post_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.set_changes_post) {
                    //setting the new string
                    String newCaption = currentCaption.getText().toString();
                    String newphoto;
                    if(photoHolderUri== null){
                        newphoto =currentPost.getPostImage();
                    }else{
                        newphoto = UserPhotoHandler.uriToBase64(photoHolderUri,editPosts.this);
                    }
                    //preparing the changes
                    changes.put("pid",String.valueOf(currentPost.getPostId()));
                    changes.put("postText",newCaption);
                    changes.put("postImage",newphoto);
                    editPostsVM.editPost(currentPost,changes);
                    return true;
                } else if (id == R.id.delete_post) {
                    editPostsVM.deletePost(currentPost);
                    return true;
                }  else if (id == R.id.delete_photo){
                    changes.put("pid",String.valueOf(currentPost.getPostId()));
                    changes.put("postText",currentCaption.getText().toString());
                    changes.put("postImage",null);
                    editPostsVM.editPost(currentPost,changes);
                    return true;
                }
                else {
                    return false;
                }
            });

            popup.show();

        });
        //letting the user attach photo from gallery
        galleryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoHandler.checkPermissionAndOpenGallery();

            }
        });
        //letting the user take a photo
        cameraPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoHandler.askCameraPermissions();

            }
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
    //setting the current post
    public void setCurrentPost(){
        this.userName.setText(currentPost.getUsername());
        this.userLogo.setImageBitmap(UserPhotoHandler.base64ToBitmap(currentPost.getUserPic()));
        this.currentCaption.setText(currentPost.getPostText());
        if(currentPost.getPostImage()!=null){
            this.imageView.setImageBitmap(UserPhotoHandler.base64ToBitmap(currentPost.getPostImage()));
        }
    }
    //setting the changes
    public void setChangeCurrentPost(){
        currentPost.setPostImage(changes.get("postImage"));
        currentPost.setPostText(changes.get("postText"));
        PostManager.getInstance().setPost(currentPost);
    }




}