package adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Utilities.UserPhotoHandler;
import ViewModals.HomePageVM;
import objects.FriendReqRecieved;


public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {

    private List<FriendReqRecieved> friendRequestList;
    private Context context;
    private FriendsListener clickListener;

    // Constructor
    public FriendRequestAdapter(Context context, FriendsListener clickListener) {
        this.context = context;
        this.clickListener=clickListener;
        friendRequestList = new ArrayList<>();
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_request_layout, parent, false);
        return new FriendRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position) {
        FriendReqRecieved friendRequest = friendRequestList.get(position);
        holder.friendUsername.setText(friendRequest.getDisplayName());
        FriendReqRecieved friendReq = friendRequestList.get(position);
        holder.friendImage.setImageBitmap(UserPhotoHandler.base64ToBitmap(friendRequest.getProfilePic()));
        holder.setFriendReq(friendReq);
        holder.setClickListener(clickListener);

    }

    public List<FriendReqRecieved> getFriendRequestList() {
        return friendRequestList;
    }

    @Override
    public int getItemCount() {
        return friendRequestList.size();
    }

    // Method to update the list of friend requests
    public void updateFriendRequests(List<FriendReqRecieved> newFriendRequestList) {
        friendRequestList = newFriendRequestList;
        notifyDataSetChanged();
    }
    // Interface for the listener so that the activity can handle the actions
    public interface FriendsListener {
        void aprovingListener(String username);
        void decliningListener(String username);

    }


    // ViewHolder class that holds references to the views for each data item
    public static class FriendRequestViewHolder extends RecyclerView.ViewHolder {
        ImageView friendImage;
        TextView friendUsername;
        ImageButton approveReqBtn, declineReqBtn;
        FriendReqRecieved friendReq;
        FriendsListener clickListener;



        public FriendRequestViewHolder(View itemView) {
            super(itemView);
            friendImage = itemView.findViewById(R.id.friendImage);
            friendUsername = itemView.findViewById(R.id.friendUsername);
            approveReqBtn = itemView.findViewById(R.id.approve_req_btn);
            declineReqBtn = itemView.findViewById(R.id.decline_req_btn);
            // Approve button listener
            approveReqBtn.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.aprovingListener(friendReq.getUserId());
                }

            });
            // Decline button listener
            declineReqBtn.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.decliningListener(friendReq.getUserId());
                    }
            });

        }
        // Setter methods
        public void setFriendReq(FriendReqRecieved friendReq) {
            this.friendReq = friendReq;
        }
        public void setClickListener(FriendsListener clickListener) {
            this.clickListener = clickListener;
        }
    }





}
