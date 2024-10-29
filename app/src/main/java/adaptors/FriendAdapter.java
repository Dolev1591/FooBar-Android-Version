package adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Utilities.UserPhotoHandler;
import activities.HomePageActivity;
import entities.Post;
import objects.Friend;
//adapter for the friends list
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private List<Friend> friendList;
    private Context context;


    public FriendAdapter(Context context) {
        this.context = context;
        this.friendList = new ArrayList<>();

    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_item, parent, false);
        return new FriendViewHolder(view,friendList,context);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend friend = friendList.get(position);
        holder.friendUsername.setText(friend.getDisplayName());
        holder.friendImage.setImageBitmap(UserPhotoHandler.base64ToBitmap(friend.getProfilePic()));

    }

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    //view holder for the friends list
    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        ImageView friendImage;
        TextView friendUsername;

        public FriendViewHolder(View itemView,List<Friend> friendsList,Context context) {
            super(itemView);
            friendImage = itemView.findViewById(R.id.friendImage);
            friendUsername = itemView.findViewById(R.id.friendUsername);
        }
    }


    // Method to update the list of friends
    public void updateFriends(List<Friend> newFriendList) {
        friendList = newFriendList;
        notifyDataSetChanged();
    }
}