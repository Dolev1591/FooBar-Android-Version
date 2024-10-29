package APIs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;

import java.util.HashMap;

import APIs.Services.UserServiceApi;
import Responses.ApiResponse;
import Utilities.MyApplication;
import Utilities.TokenInterceptor;
import Utilities.userDetailsManager;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import objects.User;

public class UserApi {
    private UserServiceApi userServiceApi;
    private Retrofit retrofit;

    //constructor
    public UserApi() {
        String token = userDetailsManager.getInstance().getToken();
        TokenInterceptor tokenInterceptor = new TokenInterceptor(token);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();
        //create the retrofit object
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.getContext().getString(R.string.BASEURL))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        userServiceApi = retrofit.create(UserServiceApi.class);
    }
    //get the user details
    public void getUser(String username, MutableLiveData<ApiResponse<User>> userLiveData) {
        userServiceApi.getUser(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userLiveData.postValue(new ApiResponse<>
                            (response.body(), "User fetched successfully", true));
                } else {
                    userLiveData.postValue(new ApiResponse<>
                            (null, "Failed to fetch user", false));
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                userLiveData.postValue(new ApiResponse<>
                        (null, "Error: " + t.getMessage(), false));
            }
        });
    }

    //update the user details
    public void updateUser(String username, HashMap<String, String> userinfo,
                           MutableLiveData<ApiResponse<User>> userLiveData) {
        userServiceApi.updateUser(username, userinfo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    userLiveData.postValue
                            (new ApiResponse<>(null
                                    , "User updated successfully.", true));
                } else {
                    userLiveData.postValue
                            (new ApiResponse<>
                                    (null, "Failed to update user", false));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                userLiveData.postValue(new ApiResponse<>
                        (null, "Error: " + t.getMessage(), false));
            }
        });
    }


    //delete the user
    public void deleteUser(String username, MutableLiveData<ApiResponse<User>> resultLiveData) {
        userServiceApi.deleteUser(username).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    resultLiveData.postValue(new ApiResponse<>
                            (null, "User deleted successfully", true));
                } else {
                    resultLiveData.postValue(new ApiResponse<>
                            (null, "Failed to delete user", false));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resultLiveData.postValue(new ApiResponse<>
                        (null, "Error: " + t.getMessage(), false));
            }
        });
    }
}