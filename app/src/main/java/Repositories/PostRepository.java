package Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import APIs.PostApi;
import DAOs.PostDao;
import DB.AppDB;
import Responses.ApiResponse;
import Responses.FriendPostsResponse;
import Utilities.LikeStatus;
import Utilities.userDetailsManager;
import entities.Post;
// Post repository
public class PostRepository {
    private PostDao dao;
    PostListData postListData;
    private PostApi api;
    MutableLiveData<Post> postRoom;
    // Constructor
    public PostRepository(Application application) {
        String username = userDetailsManager.getInstance().getUsername();
        AppDB db = AppDB.getDatabase(application, username);
        dao = db.postDao();
        postListData = new PostListData();
        api = new PostApi(dao, postListData);

    }

    public LiveData<ApiResponse<List<Post>>> getPostListData() {
        return postListData;
    }

    public class PostListData extends MutableLiveData<ApiResponse<List<Post>>> {

        public PostListData() {
            super();
            setValue(new ApiResponse<>(new LinkedList<>(), "", true));
        }

        @Override
        protected void onActive() {
            refreshPosts();
        }

        public LiveData<ApiResponse<List<Post>>> getAllPosts() {
            return postListData;
        }

        public void refreshPosts() {
            new Thread(() -> {
                List<Post> posts = dao.index();
                postListData.postValue(new ApiResponse<>
                        (posts, "Posts fetched successfully", true));
            }).start();
        }


    }


    // Get all posts
    public LiveData<ApiResponse<List<Post>>> getAll() {
        return postListData.getAllPosts();

    }
    // Get user's personal posts
    public void  getUserPosts
            (MutableLiveData<ApiResponse<FriendPostsResponse>>  userListPosts, String username) {
         api.getUserPosts(userListPosts,username);
    }
    // Add post
    public void add(Post post) {
        api.createPost(post);
        postListData.refreshPosts();

    }
    // Edit post
    public void editPost(Post post, HashMap<String,String> changes) {
        api.editPost(post,changes);
    }
    // Delete post
    public void delete(Post post) {
        api.deletePost(post);
    }
    // Reload
    public void reload() {
        api.get();
    }
    // Get post room data
    public MutableLiveData<Post> getPostRoomData() {
        this.postRoom = api.getPostRoomData();
        return postRoom;
    }
    // Change likes status
    public void changeLikesStatus(String postId, String username,
                             LikeStatus ls,MutableLiveData<String> likesLive){
        api.changeLike(postId,username,ls,likesLive);
    }

}










