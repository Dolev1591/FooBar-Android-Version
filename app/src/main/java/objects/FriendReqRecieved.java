package objects;
// Friend request recieved object
public class FriendReqRecieved {
    private String userId;
    private String displayName;
    private String profilePic;

    // Constructor
    public FriendReqRecieved(String userId, String displayName, String profilePic) {
        this.userId = userId;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
