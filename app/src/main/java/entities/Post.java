package entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// Post entity
@Entity
public class Post {
    public static final int DEFUALTLIKES = 0;
    @PrimaryKey(autoGenerate = true)
    private int roomID;

    @SerializedName("_id")
    private String postId;

    private String username;
    private String posterUsername;

    private String postTime; // Consider using a Date type if your API returns a date format that Retrofit can parse
    private String userPic;
    private String postText;
    private String postImage;
    @SerializedName("likes")
    private List<String> likes;
    @SerializedName("likeCount")
    private int likeCount;


    // Constructor
    public Post(String posterUsername,String username, String postTime, String userPic,
                String postText, String postImage, List<String> likes, int likeCount) {
        this.posterUsername = posterUsername;
        this.username = username;
        this.postTime = postTime;
        this.userPic = userPic;
        this.postText = postText;
        this.postImage = postImage;
        this.likes = likes;
        this.likeCount = likeCount;
    }
    // Constructor
    @Ignore
    public Post(String posterUsername,String username, String postTime, String userPic,
                String postText, String postImage) {
        this.posterUsername = posterUsername;
        this.username = username;
        this.postTime = postTime;
        this.userPic = userPic;
        this.postText = postText;
        this.postImage = postImage;
        this.likes = new ArrayList<>();
        this.likeCount = DEFUALTLIKES;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    // Getters
    public String getPosterUsername() {
        return posterUsername;
    }

    public String getPostTime() {
        return postTime;
    }

    public String getUserPic() {
        return userPic;
    }

    public String getPostText() {
        return postText;
    }

    public String getPostImage() {
        return postImage;
    }

    // Setters
    public void setPosterUsername(String posterUsername) {
        this.posterUsername = posterUsername;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }



    public void editPost(HashMap<String,String> changes){
        if(changes.containsKey("text")){
            this.postText = changes.get("text");
        }
        if(changes.containsKey("picture")){
            this.postImage = changes.get("picture");
        }

    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}