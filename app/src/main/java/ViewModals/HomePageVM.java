package ViewModals;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import Repositories.FriendsRepository;
import Repositories.PostRepository;
import Repositories.UserRepository;
import Responses.ApiResponse;
import Responses.FriendPostsResponse;
import Utilities.LikeStatus;
import Utilities.userDetailsManager;
import entities.Post;
import objects.Friend;
import objects.User;
// Home page view model class to handle the home page actions
public class HomePageVM extends AndroidViewModel {
    private UserRepository userRepo;
    private PostRepository postsRepo;
    private FriendsRepository friendsRepo;
    private MutableLiveData<ApiResponse<FriendPostsResponse>>  postListData;
    private MutableLiveData<ApiResponse<User>> userData;
    private MutableLiveData<List<Friend>> friendsLiveData;
    private MutableLiveData<ApiResponse<String>> friendsMessageLiveData;
    private MutableLiveData<String> likesStatusLiveData;
    private MutableLiveData<ApiResponse<User>> CurrentOperatorLiveData;


    public HomePageVM(Application application) {
        super(application);
        this.userRepo = new UserRepository();
        this.postsRepo = new PostRepository(application);
        this.friendsRepo = new FriendsRepository();
        this.postListData = new MutableLiveData<>();
        this.userData = new MutableLiveData<>();
        this.friendsLiveData = new MutableLiveData<>();
        this.friendsMessageLiveData = new MutableLiveData<>();
        this.likesStatusLiveData = new MutableLiveData<>();
        this.CurrentOperatorLiveData = new MutableLiveData<>();

    }

    public MutableLiveData<ApiResponse<User>>  getCurrentOperatorLiveData() {
        return CurrentOperatorLiveData;
    }

    public MutableLiveData<String> getLikesStatusLiveData() {
        return likesStatusLiveData;
    }

    public MutableLiveData<ApiResponse<FriendPostsResponse>>  getPostListData() {
        return postListData;
    }

    public MutableLiveData<ApiResponse<User>> getUserData() {
        return userData;
    }

    public MutableLiveData<List<Friend>> getFriendsLiveData() {
        return friendsLiveData;
    }

    public MutableLiveData<ApiResponse<String>> getFriendsMessageLiveData() {
        return friendsMessageLiveData;
    }
    // get all posts

    public void getUserPosts(String username) {
        postsRepo.getUserPosts(postListData, username);
    }
    // Get user posting
    public void getUser(String username) {
        userRepo.getUser(username, userData);
    }
    // Get friends
    public void addFriend(String friendUsername){
        String username = userDetailsManager.getInstance().getUsername();
        friendsRepo.newFriendRequest(username,friendUsername,friendsMessageLiveData);
    }
    // Get friends
    public void deleteFriend(String friendUsername){
        String username = userDetailsManager.getInstance().getUsername();
        friendsRepo.deleteFriend(username,friendUsername,friendsMessageLiveData);
    }
    // Get friends
    public void deleteRequest(String friendUsername){
        String username = userDetailsManager.getInstance().getUsername();
        friendsRepo.deleteFriend(username,friendUsername,friendsMessageLiveData);
    }
    // Get friends
    public void changeLikes(String postId, LikeStatus likeStatus, String username) {
        postsRepo.changeLikesStatus(postId, username, likeStatus, likesStatusLiveData);
    }
    // Get friends
    public void getCurrentOperator(){
        String username = userDetailsManager.getInstance().getUsername();
        userRepo.getUser(username,CurrentOperatorLiveData);
    }




}
