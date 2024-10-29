package ViewModals;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;

import Repositories.PostRepository;
import Repositories.UserRepository;
import Responses.ApiResponse;
import Utilities.userDetailsManager;
import entities.Post;
import objects.User;
// Edit post view model class to handle the post actions
public class EditPostVM  extends AndroidViewModel {

    private UserRepository userRepo;
    private PostRepository postsRepo;

    private LiveData<ApiResponse<List<Post>>> postListData;
    private MutableLiveData<ApiResponse<User>> userData;


    public LiveData<ApiResponse<List<Post>>> getPostListData() {
        return postListData;
    }

    public MutableLiveData<ApiResponse<User>> getUserData() {
        return userData;
    }

    public EditPostVM(Application application) {
        super(application);
        this.userRepo = new UserRepository();
        this.postsRepo = new PostRepository(application);
        this.postListData = postsRepo.getPostListData();
        this.userData = new MutableLiveData<>();
    }
    // Add post
    public void editPost(Post post, HashMap<String, String> data ) {
        postsRepo.editPost(post,data);
    }
    // Delete post
    public void deletePost(Post post ) {
        postsRepo.delete(post);
    }
    // Get user posting
    public void getUserPosting() {
        String currentUser = userDetailsManager.getInstance().getUsername();
        userRepo.getUser(currentUser, userData);
    }


}
