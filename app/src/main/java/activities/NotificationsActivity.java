package activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;

import Utilities.userDetailsManager;
import ViewModals.FeedVM;
import ViewModals.NotificationsVM;
import adaptors.FriendRequestAdapter;
import adaptors.PostAdapter;
import objects.FriendReqRecieved;

public class NotificationsActivity extends AppCompatActivity implements FriendRequestAdapter.FriendsListener {

    private NotificationsVM notificationsVM;
    private FriendRequestAdapter notificationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        RecyclerView notificationsRv = findViewById(R.id.notifications_rc);
        notificationsVM = new  ViewModelProvider(this).get(NotificationsVM.class);
        String CurrentUser = userDetailsManager.getInstance().getUsername();
        //getting the friend requests
        notificationsVM.getFriendReqRecievedLiveData()
                .observe(this, friendReqRecievedApiResponse -> {
          if(friendReqRecievedApiResponse!=null){
                notificationsAdapter.updateFriendRequests(friendReqRecievedApiResponse.getData());
        }});
        notificationsAdapter= new FriendRequestAdapter(this,this);
        notificationsRv.setAdapter(notificationsAdapter);
        notificationsRv.setLayoutManager(new LinearLayoutManager(this));
        notificationsVM.getFriendRequests(CurrentUser);
        //handling the actions on a request
        notificationsVM.getFriendsMessageLiveData()
                .observe(this, friendReqRecievedApiResponse -> {
            if(friendReqRecievedApiResponse!=null){
                Toast toast = Toast.makeText(NotificationsActivity.this,
                        friendReqRecievedApiResponse.getData(), Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(NotificationsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void aprovingListener(String username) {
        notificationsVM.approveReq(username);
    }

    @Override
    public void decliningListener(String username) {
        notificationsVM.deleteReq(username);
    }
}
