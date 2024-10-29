package activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


import Utilities.userDetailsManager;
import ViewModals.ShowFriendsVM;
import adaptors.FriendAdapter;
import adaptors.FriendRequestAdapter;
import objects.Friend;

public class ShowFriendsActivity extends AppCompatActivity {
    private ShowFriendsVM showFriendsVM;
    private FriendAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_friends);
        //attaching to the views
        RecyclerView friendsRecyclerView = findViewById(R.id.friendsRecyclerView);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showFriendsVM = new ViewModelProvider(this).get(ShowFriendsVM.class);
        String CurrentUser = userDetailsManager.getInstance().getUsername();
        //getting the friends list
        showFriendsVM.getFriendsLiveData().observe(this, friendsApiResponse -> {
            if(friendsApiResponse!=null){
                adapter.setFriendList(friendsApiResponse);
            }
        });
        adapter = new FriendAdapter(this);
        friendsRecyclerView.setAdapter(adapter);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showFriendsVM.getUsersFriends(CurrentUser);

    }

}