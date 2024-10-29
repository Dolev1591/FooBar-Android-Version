package Repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;

import APIs.LoginApi;
import APIs.RegistrationApi;
import DAOs.UserDao;
import DB.AppDB;

// Registration repository
public class RegistrationRepo {
    MutableLiveData<String> RegistrationMessage;
    private RegistrationApi RegistrationApi;
    // Constructor
    public RegistrationRepo() {
        this.RegistrationMessage = new MutableLiveData<>();
        this.RegistrationApi = new RegistrationApi(RegistrationMessage);
    }
    // Register
    public LiveData<String> register(HashMap<String, String> credentials){
        RegistrationApi.post(credentials);
        return RegistrationMessage;

    }
    public LiveData<String> getRegistrationMessage() {
        return RegistrationMessage;
    }
}
