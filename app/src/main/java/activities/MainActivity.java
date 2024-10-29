package activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import Utilities.LikeStatus;
import Utilities.userDetailsManager;
import ViewModals.FeedVM;
import adaptors.PostAdapter;
import objects.MarginItemDecoration;
import objects.User;

public class MainActivity extends AppCompatActivity implements PostAdapter.LikesListner {

    private User user;
    private PostAdapter adapter;
    private FeedVM feedVM;
    private ImageButton editProfileButton;
    private ImageButton notificationsButton;
    SharedPreferences sharedPreferences;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editProfileButton = findViewById(R.id.search_Button);
        notificationsButton = findViewById(R.id.notification_button);
        this.user= new User();
        // save mode if exit the app and go back again
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        int verticalSpaceHeight = getResources().getDimensionPixelSize(R.dimen.vertical_margin);
        lstPosts.addItemDecoration(new MarginItemDecoration(verticalSpaceHeight));
        feedVM= new ViewModelProvider(this).get(FeedVM.class);
        //getting the posts data
        feedVM.getPostListData().observe(this, posts -> {
            if (posts != null) {
                adapter.setPosts(posts.getData());
                if(!feedVM.getPostListData().getValue().isSuccess()){
                    Toast toast = Toast.makeText(MainActivity.this,
                            feedVM.getPostListData().getValue().getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //getting the user data
        feedVM.getUserData().observe(this, userApiResponse -> {
            if (userApiResponse != null && userApiResponse.getData() != null){
                this.user = userApiResponse.getData();
                adapter.setUser(user);

            }
        });
        //getting the likes status when like actions occurred
        feedVM.getLikesStatusLiveData().observe(this, s -> {
            if(s!=null){
                if(!s.equals("success")){
                    Toast toast = Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG);
                    toast.show();

                }
            }
        });
        feedVM.getUserPosting();
        feedVM.getUserData();
        adapter= new PostAdapter(this,this.user,this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        feedVM.reload();
        //setting the swipe refresh-meanwhile there is no reason
        //for refreshing
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            feedVM.reload();
            swipeRefreshLayout.setRefreshing(false);

        });
        //menu button
       ImageButton menuButton = findViewById(R.id.mainMenu_button);
        menuButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.logout_item) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (id == R.id.my_profile_item) {
                    String CurrentUser = userDetailsManager.getInstance().getUsername();
                    Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                    intent.putExtra("username",CurrentUser);
                    startActivity(intent);
                }
                return true;
            });
            popup.show();
        });
        //new post button
        ImageButton addPost = findViewById(R.id.add_new_post_button);
        addPost.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
            startActivity(intent);

        });
        //friends button
        ImageButton friendsButton = findViewById(R.id.friends_button);
        friendsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowFriendsActivity.class);
            startActivity(intent);
        });

        //edit profile btn
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });
        //notifications button
        notificationsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
            startActivity(intent);
        });


    }
    //handling the likes
    @Override
    public void changeLikesStatus(String postId,LikeStatus ls,String usermame) {
        feedVM.changeLikes(postId,ls,usermame);
    }

    //refreshing the data
    @Override
    protected void onResume() {
        super.onResume();
        feedVM.reload();
        adapter.notifyDataSetChanged();

    }


}