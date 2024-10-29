package ViewModals;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import Repositories.PostRepository;
import Repositories.UserRepository;
import Responses.ApiResponse;
import Utilities.LikeStatus;
import Utilities.userDetailsManager;
import entities.Post;
import objects.User;
// Feed view model class to handle the feed actions
public class FeedVM extends AndroidViewModel {
    private UserRepository userRepo;
    private PostRepository postsRepo;

    private LiveData<ApiResponse<List<Post>>> postListData;
    private MutableLiveData<ApiResponse<User>> userData;
    private MutableLiveData<String> likesStatusLiveData;

    public MutableLiveData<String> getLikesStatusLiveData() {
        return likesStatusLiveData;
    }

    public LiveData<ApiResponse<List<Post>>> getPostListData() {
        return postListData;
    }

    public MutableLiveData<ApiResponse<User>> getUserData() {
        return userData;
    }

    public FeedVM(Application application) {
        super(application);
        this.userRepo = new UserRepository();
        this.postsRepo = new PostRepository(application);
        this.postListData = postsRepo.getPostListData();
        this.userData = new MutableLiveData<>();
        this.likesStatusLiveData = new MutableLiveData<>();
    }
    // get all posts
    public void getAllPosts() {
        postsRepo.getAll();
    }
    // reload posts
    public void reload() {
        postsRepo.reload();
    }
    // Get user posting
    public void getUserPosting() {
        String currentUser = userDetailsManager.getInstance().getUsername();
        userRepo.getUser(currentUser, userData);
    }
    // Change likes
    public void changeLikes(String postId, LikeStatus likeStatus, String username) {
        postsRepo.changeLikesStatus(postId, username, likeStatus, likesStatusLiveData);
    }

}
