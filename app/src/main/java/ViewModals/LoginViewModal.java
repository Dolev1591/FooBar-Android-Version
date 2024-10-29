package ViewModals;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import Repositories.LoginRepository;
//  Login view model class to handle the login actions
public class LoginViewModal extends AndroidViewModel {

    private LoginRepository repository;
    private MutableLiveData<String> loginResult;

    public LoginViewModal(Application application) {
        super(application);
        this.repository = new LoginRepository();
    }
    public LiveData<String> getLoginResult() {
        if (loginResult == null) {
            loginResult = new MutableLiveData<>();
        }
        return loginResult;
    }
    //  Login
    public void  logIn(String Username,String Password) {
        HashMap<String,String > cred = new HashMap<>();
        cred.put("username",Username);
        cred.put("password",Password);
        LiveData<String> result = repository.login(cred);
        result.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String token) {
                loginResult.postValue(result.getValue());
                result.removeObserver(this); // We only want to observe this once
            }
        });
    }
}
