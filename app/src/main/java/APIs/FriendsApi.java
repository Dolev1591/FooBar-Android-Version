package APIs;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import APIs.Services.FriendsServiceApi;
import Responses.ApiResponse;
import Responses.SimpleResponse;
import Utilities.MyApplication;
import Utilities.TokenInterceptor;
import Utilities.userDetailsManager;
import objects.Friend;
import objects.FriendReqRecieved;
import objects.FriendRequest;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// API for the friends actions
public class FriendsApi {
    private FriendsServiceApi friendsServiceApi;
    Gson gson;

    public FriendsApi() {
        //adding token and connecting to server
        String token = userDetailsManager.getInstance().getToken();
        TokenInterceptor tokenInterceptor = new TokenInterceptor(token);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.getContext().getString(R.string.BASEURL))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        friendsServiceApi = retrofit.create(FriendsServiceApi.class);
        gson = new Gson();
    }
    //get users friends
    public MutableLiveData<List<Friend>> getUsersFriends
    (String userId, MutableLiveData<List<Friend>> friendsLiveData) {
        friendsServiceApi.getUsersFriends(userId).enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if (response.isSuccessful()) {
                    friendsLiveData.postValue(response.body());
                } else {
                    friendsLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                friendsLiveData.postValue(null);
            }
        });
        return friendsLiveData;


    }
    //
    public MutableLiveData<ApiResponse<String>> newFriendRequest
    (FriendRequest friendRequest, MutableLiveData<ApiResponse<String>> resultLiveData) {

        friendsServiceApi.newFriendRequest
                (friendRequest.getUsername(),friendRequest).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    resultLiveData.postValue(new ApiResponse<>("Request has sent!", "Success", true));
                } else if (response.errorBody() != null) {
                    try {
                        String errorBody = response.errorBody().string();
                        JsonObject jsonObject = gson.fromJson(errorBody, JsonObject.class);
                        String errorMessage = jsonObject.get("message").getAsString();
                        resultLiveData.postValue
                                (new ApiResponse<>(errorMessage, "Error", false));
                    } catch (IOException e) {
                        resultLiveData.postValue
                                (new ApiResponse<>("request already sent..", "Error", false));
                    }
                }
            }
            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                resultLiveData.postValue
                        (new ApiResponse<>(t.getMessage(),"Error",false));
            }
        });

        return resultLiveData;
    }

    public MutableLiveData<ApiResponse<String>> approveFriendRequest
            (String userId, String friendId,MutableLiveData<ApiResponse<String>> resultLiveData) {


        friendsServiceApi.approveFriendReq(userId, friendId).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resultLiveData.postValue(new ApiResponse<>
                            ("Friend Added!", "Success", true));
                } else {
                    resultLiveData.postValue(new ApiResponse<>
                            ("Enable To Approve", "Error", false));
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                resultLiveData.postValue(new ApiResponse<>
                        (t.getMessage(),"Error",false));
            }
        });

        return resultLiveData;
    }
    //delete friend or friend request
    public MutableLiveData<ApiResponse<String>>  deleteFriend
            (String userId, String friendId,MutableLiveData<ApiResponse<String>> resultLiveData) {

        friendsServiceApi.deleteFriend(userId, friendId).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {

                if (response.isSuccessful() ) {
                    resultLiveData.postValue(new ApiResponse<>
                            ("Deleted Successfully", "Success", true));
                } else {
                    resultLiveData.postValue(new ApiResponse<>
                            ("Try again later...", "Error", false));
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                resultLiveData.postValue
                        (new ApiResponse<>(t.getMessage(),"Error",false));
            }
        });

        return resultLiveData;
    }
    //get friend requests
    public void getFriendReq
            (String username, MutableLiveData<ApiResponse<List<FriendReqRecieved>>> friendReqData) {
        Call<List<FriendReqRecieved>> call = friendsServiceApi.getFriendRequests(username);
        call.enqueue(new Callback<List<FriendReqRecieved>>() {
            @Override
            public void onResponse
                    (Call<List<FriendReqRecieved>> call, Response<List<FriendReqRecieved>> response) {
                if (response.isSuccessful()) {
                    friendReqData.postValue(new ApiResponse<>
                            (response.body(), "Success", true));
                } else {
                    friendReqData.postValue(new ApiResponse<>
                            (null, "Error", false));
                }
            }

            @Override
            public void onFailure(Call<List<FriendReqRecieved>> call, Throwable t) {
                friendReqData.postValue
                        (new ApiResponse<>(null, t.getMessage(), false));
            }
        });
    }








}
