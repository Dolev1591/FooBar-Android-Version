package Repositories;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.HashMap;

import APIs.LoginApi;
import DAOs.UserDao;
import DB.AppDB;

// Login repository
public class LoginRepository {
    LiveData<String> loginMessage;

    private LoginApi loginApi;

    public LoginRepository() {
        this.loginApi = new LoginApi();
    }
    // Login
    public LiveData<String> login(HashMap<String, String> credentials){
        loginApi.post(credentials);
        this.loginMessage=loginApi.getLoginMessage();
        return loginMessage;

    }
}
