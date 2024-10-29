package APIs.Services;

import java.util.List;

import Responses.ApiResponse;
import Responses.SimpleResponse;
import entities.Post;
import objects.Friend;
import objects.FriendReqRecieved;
import objects.FriendRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
// Service API for the friends actions
public interface FriendsServiceApi {


    @GET("api/users/{id}/friends")
    Call<List<Friend>> getUsersFriends(@Path("id") String username);
    @POST("api/users/{id}/friends")
    Call<SimpleResponse> newFriendRequest(@Path("id") String displayname,
                                           @Body FriendRequest friendRequest);

    @PATCH("api/users/{id}/friends/{fid}")
    Call<SimpleResponse> approveFriendReq(@Path("id") String username, @Path("fid") String friendsUsername);
    @DELETE("api/users/{id}/friends/{fid}")
    Call<SimpleResponse> deleteFriend(@Path("id") String username, @Path("fid") String friendsUsername);
    @GET("api/users/{id}/friend-requests")
    Call<List<FriendReqRecieved>> getFriendRequests(@Path("id") String username);

}
