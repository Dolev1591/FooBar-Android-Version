package Utilities;

import entities.Post;
// Post manager class to manage post
public class PostManager {
    private static PostManager instance;
    private Post post;

    // Private constructor to prevent instantiation
    private PostManager() {
    }

    // Synchronized method to control simultaneous access in multithreaded environments
    public static synchronized PostManager getInstance() {
        if (instance == null) {
            instance = new PostManager();
        }
        return instance;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
