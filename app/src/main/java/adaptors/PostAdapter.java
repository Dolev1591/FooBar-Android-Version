package adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import Utilities.LikeStatus;
import Utilities.PostManager;
import Utilities.UserPhotoHandler;
import Utilities.userDetailsManager;
import activities.HomePageActivity;
import activities.editPosts;
import entities.Post;
import objects.User;
// Adapter for the posts list
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private User user;
    private LikesListner likesListner;
    private Context context;
    private List<Post> posts;

    // Constructor
    public PostAdapter(Context context, User  user,LikesListner likesListner) {
        this.context = context;
        this.user = user;
        posts = new ArrayList<>();
        this.likesListner = likesListner;
    }



    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_layout, parent, false);
        return new PostViewHolder(v, context,this);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        // attaching to the views
        Post currentPost = posts.get(position);
        holder.postAuthor.setText(currentPost.getUsername());
        holder.Caption.setText(currentPost.getPostText());
        holder.timeStamp.setText(currentPost.getPostTime());
        String Likes = "Likes: " + currentPost.getLikeCount();
        holder.LikesCounter.setText(Likes);
        //dont let a user edit posts not his own
        if(!currentPost.getPosterUsername().equals(userDetailsManager.getInstance().getUsername())){
            holder.editButton.setVisibility(View.GONE);
        }
        else{
            holder.editButton.setVisibility(View.VISIBLE);
        }
        //handling photo
        holder.userPhoto.setImageBitmap(UserPhotoHandler.base64ToBitmap(currentPost.getUserPic()));
        if (currentPost.getPostImage() != null) {
            // Check if the stub is already inflated
            if (holder.stubImage.getParent() != null) {
                View inflated = holder.stubImage.inflate();
                holder.postImage = inflated.findViewById(R.id.post_image);
            } else {
                // The ViewStub no longer has a parent, which means it was already inflated
                holder.postImage = holder.itemView.findViewById(R.id.post_image);
            }
            holder.postImage.setVisibility(View.VISIBLE);
            holder.postImage.setImageBitmap(UserPhotoHandler.base64ToBitmap(currentPost.getPostImage()));
        }
        else {
            // If there's no image, ensure the ImageView is gone (to handle view recycling)
            if (holder.postImage != null) {
                holder.postImage.setVisibility(View.GONE);
            }
        }


        // Edit button click is handled within the ViewHolder
    }
    public interface LikesListner {
        void changeLikesStatus(String postId, LikeStatus likeStatus,String usermame);
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {
        private static final int ADDLIKE = 1;
        private static final int REMOVELIKE= -1;



        private  LikesListner likesListner;

        private final TextView postAuthor;
        private final TextView Caption;
        private ImageView postImage;
        private TextView timeStamp;
        private TextView LikesCounter;
        private ImageButton shareButton;
        private ImageButton editButton;

        private Context context;
        private final ImageView userPhoto;
        private final ViewStub stubImage;
        private final View itemView;

        private PostViewHolder(View itemView, Context context, PostAdapter adapter) {
            super(itemView);
            this.context = context;
            postAuthor = itemView.findViewById(R.id.nickname);
            Caption = itemView.findViewById(R.id.caption);
            this.userPhoto = itemView.findViewById(R.id.userlogo);
            stubImage = itemView.findViewById(R.id.post_image_stub);
            timeStamp = itemView.findViewById(R.id.timeStamp);
            this.LikesCounter = itemView.findViewById(R.id.Likes_Counter);
            this.itemView = itemView;
            ImageButton likeButton = itemView.findViewById(R.id.button_like);
            ImageButton commentButton = itemView.findViewById(R.id.button_comment);
            this.shareButton = itemView.findViewById(R.id.button_send);
            this.editButton = itemView.findViewById(R.id.editButton);
            List<Post> posts = adapter.getPosts();
            this.likesListner = adapter.getLikesListner();
            // Set up the buttons
            //edit button
            editButton.setOnClickListener(v -> {
                List<Post> freshPosts = adapter.getPosts();
                Intent intent = new Intent(this.context, editPosts.class);
                Post currentPost =freshPosts.get(getAdapterPosition());
                PostManager.getInstance().setPost(currentPost);
                context.startActivity(intent);

            });
            //share button
            shareButton.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(this.context, shareButton);
                popup.getMenuInflater().inflate(R.menu.share_menu, popup.getMenu());
                popup.show();
            });
            //the button that passes you to the user profile
            postAuthor.setOnClickListener(v -> {
                List<Post> freshPosts = adapter.getPosts();
                int position = getAdapterPosition();
                Post current = freshPosts.get(position);
                Intent intent = new Intent(this.context, HomePageActivity.class);
                intent.putExtra("username", current.getPosterUsername());
                context.startActivity(intent);
            });
            //like button
            likeButton.setOnClickListener(view -> {
                List<Post> freshPosts = adapter.getPosts();
                User user = adapter.getUser();
                int position = getAdapterPosition();
                Post current = freshPosts.get(position);
                String username = user.getUsername();
                //
                if (!current.getLikes().contains(user.get_id())){
                   current.setLikeCount(current.getLikeCount() + ADDLIKE);
                   current.getLikes().add(user.get_id());
                    String newLikes ="Likes: " + current.getLikeCount();
                     LikesCounter.setText(newLikes);
                     LikeStatus likeStatus = new LikeStatus(true);
                    this.likesListner.changeLikesStatus(current.getPostId(),likeStatus,username);
                }
                else if (current.getLikes().contains(user.get_id())){
                    current.setLikeCount(current.getLikeCount() + REMOVELIKE);
                    current.getLikes().remove(user.get_id());
                    String newLikes ="Likes: " + current.getLikeCount();
                    LikesCounter.setText(newLikes);
                    LikeStatus likeStatus = new LikeStatus(false);
                    this.likesListner.changeLikesStatus(current.getPostId(),likeStatus,username);

                }
            });
        }



    }
    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Helper method to update posts data
    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }
    //getters setters
    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public User getUser() {
        return user;
    }

    public LikesListner getLikesListner() {
        return likesListner;
    }

    public void setLikesListner(LikesListner likesListner) {
        this.likesListner = likesListner;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // Add any other required methods here...
}
