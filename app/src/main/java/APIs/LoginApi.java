package APIs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;

import java.util.HashMap;

import APIs.Services.LoginServiceApi;
import Responses.LoginResponse;
import Utilities.MyApplication;
import Utilities.userDetailsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// API for the login actions
public class LoginApi {

    private Retrofit retrofit;
    private LoginServiceApi loginServiceApi;
    private MutableLiveData<String> loginMessage;




    //constructor
    public LoginApi() {
        retrofit= new Retrofit.Builder().
                baseUrl(MyApplication.getContext().getString(R.string.BASEURL)).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        loginServiceApi= retrofit.create(LoginServiceApi.class);
        loginMessage = new MutableLiveData<>();

    }
    //getter
    public LiveData<String> getLoginMessage() {
        return loginMessage;
    }
    //handle the login action and get login if successful
    public void post(HashMap<String, String> credentials){
        Call<LoginResponse> call = loginServiceApi.loginUser(credentials);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    LoginResponse loginResponse = response.body();
                    String token = loginResponse.getToken();
                    userDetailsManager.getInstance().setToken(token);
                    loginMessage.postValue(String.valueOf(response.code()));

                } else {
                    loginMessage.postValue(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginMessage.postValue("Network error!");

            }
        });
    }

    }

