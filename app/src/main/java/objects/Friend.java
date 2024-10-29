package objects;

import android.graphics.Bitmap;
// Friend object
public class Friend {
    private String username;
    private String userId;
    private String displayName;
    private String profilePic;

    public Friend(String userId, String displayName, String profilePic,String username) {
        this.userId = userId;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.username = username;
    }

    public String getUsername() {
        return username;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
