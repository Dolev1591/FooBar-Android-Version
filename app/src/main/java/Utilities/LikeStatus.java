package Utilities;
// Like status class to hold like status
public class LikeStatus {
    private boolean isLiked;

    public LikeStatus(boolean isLiked) {
        this.isLiked = isLiked;
    }

    // Getter and setter
    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
