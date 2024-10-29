package Repositories;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;

import APIs.UserApi;
import Responses.ApiResponse;
import objects.User;
// User repository
public class UserRepository {
    private final UserApi userApi;

    public UserRepository() {
        this.userApi = new UserApi(); // Initialize UserApi
    }

    // Method to get user details
    public void getUser(String username, MutableLiveData<ApiResponse<User>> userLiveData) {
        userApi.getUser(username, userLiveData);
    }

    // Method to update user details
    public void updateUser(String username, HashMap<String, String> userinfo, MutableLiveData<ApiResponse<User>> resultLiveData) {
        userApi.updateUser(username, userinfo, resultLiveData);
    }

    // Method to delete a user
    public void deleteUser(String username, MutableLiveData<ApiResponse<User>> resultLiveData) {
        userApi.deleteUser(username, resultLiveData);
    }
}