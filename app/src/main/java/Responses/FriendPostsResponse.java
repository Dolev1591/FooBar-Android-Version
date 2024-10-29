package Responses;

import java.util.List;

import entities.Post;
// Friend posts response class to hold friend posts and friend status
public class FriendPostsResponse {
    private boolean areFriends;
    private List<Post> friendPosts;

    // Constructor, getters, and setters
    public FriendPostsResponse(boolean areFriends, List<Post> friendPosts) {
        this.areFriends = areFriends;
        this.friendPosts = friendPosts;
    }

    public boolean areFriends() {
        return areFriends;
    }

    public void setAreFriends(boolean areFriends) {
        this.areFriends = areFriends;
    }

    public List<Post> getFriendPosts() {
        return friendPosts;
    }

    public void setFriendPosts(List<Post> friendPosts) {
        this.friendPosts = friendPosts;
    }
}
