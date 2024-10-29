package objects;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// User entity
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("_id")
    private String _id;

    @SerializedName("displayName")
    private String displayName;

    @SerializedName("password") // Note: Sending passwords in plain text over the network is insecure.
    private String password;

    @SerializedName("profilePic")
    private String profilePic;

    @SerializedName("friends")
    private List<String> friends;

    @SerializedName("friendRequests")
    private FriendRequests friendRequests;

    @SerializedName("posts")
    private List<String> posts; // Using String for ObjectId representation


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Inner class for friend requests
    public static class FriendRequests {
        @SerializedName("received")
        private List<String> received;

        @SerializedName("sent")
        private List<String> sent;

        // Getters and setters
        public List<String> getReceived() {
            return received;
        }

        public void setReceived(List<String> received) {
            this.received = received;
        }

        public List<String> getSent() {
            return sent;
        }

        public void setSent(List<String> sent) {
            this.sent = sent;
        }
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public FriendRequests getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(FriendRequests friendRequests) {
        this.friendRequests = friendRequests;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

}
