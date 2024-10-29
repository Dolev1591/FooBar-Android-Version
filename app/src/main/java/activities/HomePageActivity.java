package activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.HashMap;

import Utilities.LikeStatus;
import Utilities.UserPhotoHandler;
import Utilities.userDetailsManager;
import ViewModals.HomePageVM;
import adaptors.PostAdapter;
import objects.MarginItemDecoration;
import objects.User;
//the home page of a user activity
public class HomePageActivity extends AppCompatActivity implements PostAdapter.LikesListner {
    private boolean areFriends;
    private User currentOperatorUser;
    private PostAdapter adapter;
    private User currentuser;
    private HomePageVM homePageVM;


    private ImageView profileImage;
    private ImageButton friendMenu;
    private TextView displayTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //attaching to the views
        RecyclerView lstPosts = findViewById(R.id.postsRecyclerView);
        profileImage = findViewById(R.id.profile_image_hp);
        displayTextView = findViewById(R.id.home_page_nickname);
        //getting the user data from the intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int verticalSpaceHeight = getResources().getDimensionPixelSize(R.dimen.vertical_margin);
        lstPosts.addItemDecoration(new MarginItemDecoration(verticalSpaceHeight));
        friendMenu = findViewById(R.id.friend_option_button);
        //dont let the current user add himself
        if(username.equals(userDetailsManager.getInstance().getUsername())){
            friendMenu.setVisibility(View.GONE);
        }
        homePageVM = new HomePageVM(getApplication());
        //getting the user data
        homePageVM.getUserData().observe(this, user -> {
                    currentuser = user.getData();
                    displayTextView.setText(currentuser.getDisplayName());
                    profileImage.setImageBitmap(UserPhotoHandler.base64ToBitmap(currentuser.getProfilePic()));
        });
        //getting the user posts
        homePageVM.getPostListData().observe(this, posts -> {
            if (posts != null) {
                this.areFriends = posts.getData().areFriends();
                if(posts.getData().getFriendPosts()!=null){
                    adapter.setPosts(posts.getData().getFriendPosts());
                }
                setOptionsMenu();
            }
        });
        //getting the current operator data
        homePageVM.getCurrentOperatorLiveData().observe(this, s -> {
            if(s!=null){
                if(s.getMessage().equals("User fetched successfully")){
                    currentOperatorUser = s.getData();
                    adapter.setUser(currentOperatorUser);
                }
                else{
                    Toast toast = Toast.makeText(HomePageActivity.this,
                            s.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        //getting the friends action message
        homePageVM.getFriendsMessageLiveData().observe(this, message -> {
                    Toast toast = Toast.makeText(HomePageActivity.this, message.getData(),
                            Toast.LENGTH_LONG);
                    toast.show();
        });
        //calling the view modal methods
        homePageVM.getCurrentOperator();
        homePageVM.getCurrentOperator();
        homePageVM.getUser(username);
        homePageVM.getUserPosts(username);
        adapter= new PostAdapter(this,currentOperatorUser,this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        //handling likes
        homePageVM.getLikesStatusLiveData().observe(this, s -> {
            if(s!=null){
                if(!s.equals("success")){
                    Toast toast = Toast.makeText(HomePageActivity.this, s, Toast.LENGTH_LONG);
                    toast.show();

                }
            }
        });
        //setting the friend menu and handling actions
        friendMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.friend_menu, popup.getMenu());
            String currentOption = setOptionsMenu();
            popup.getMenu().getItem(0).setTitle(currentOption);
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
               String text = item.getTitle().toString();
               if(id==R.id.friendmenu_item){
                   if(text.equals("Add Friend")){
                       homePageVM.addFriend(currentuser.getUsername());
                       return true;
                   }
                   else if(text.equals("Delete Friend")){
                       homePageVM.deleteFriend(currentuser.get_id());
                       return true;
                   }
                   else{
                       return true;
                   }

               }else{
                     return false;
               }

            });

            // Show the popup menu
            popup.show();

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.friend_menu, menu);
        return true;
    }
    @Override
    public void changeLikesStatus(String PostId, LikeStatus ls, String username) {
        homePageVM.changeLikes(PostId,ls,username);


    }

    public String setOptionsMenu(){
        if(this.areFriends){
            return "Delete Friend";
        }
        else{
            return "Add Friend";
        }

    }

}