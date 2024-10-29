package ViewModals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import Repositories.LoginRepository;
import Repositories.RegistrationRepo;
//  Registration view model class to handle the registration actions
public class RegistrationViewModal extends ViewModel {
    private RegistrationRepo repository;
    private LiveData<String> RegistrationMessage;

    public RegistrationViewModal() {
        this.repository = new RegistrationRepo();
        this.RegistrationMessage = repository.getRegistrationMessage();
    }
    // Register
    public void register(String usernname, String nickname, String password, String profilePic) {
        HashMap<String,String > credentials = new HashMap<>();
        credentials.put("username",usernname);
        credentials.put("displayName",nickname);
        credentials.put("password",password);
        credentials.put("profilePic",profilePic);
        this.repository.register(credentials);
    }

    // Get registration message
    public LiveData<String> getRegistrationMessage() {
        return RegistrationMessage;
    }
}
