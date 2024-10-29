package ViewModals;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import Repositories.UserRepository;
import Responses.ApiResponse;
import Utilities.userDetailsManager;
import entities.Post;
import objects.User;
// Edit profile view model class to handle the user profile actions
public class EditProfileVM extends ViewModel {
    private UserRepository userRepo;

    private MutableLiveData<ApiResponse<User>> userData;

    public EditProfileVM() {
        this.userRepo = new UserRepository();
        this.userData = new MutableLiveData<>();
    }

    public MutableLiveData<ApiResponse<User>> getUserData() {
        return userData;
    }
    public void getUserPosting() {
        String currentUser = userDetailsManager.getInstance().getUsername();
        userRepo.getUser(currentUser, userData);
    }
    // Edit user
    public void editUser(User user, HashMap<String, String> changes) {
        userRepo.updateUser(user.getUsername(), changes, userData);
    }
    // Delete user
    public void deleteUser(User user) {
        userRepo.deleteUser(user.getUsername(), userData);
    }


}
