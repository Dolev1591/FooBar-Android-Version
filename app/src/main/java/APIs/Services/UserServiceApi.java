package APIs.Services;

import java.util.HashMap;
import java.util.List;

import objects.Friend;
import objects.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
// Service API for the user actions
public interface UserServiceApi {
    @GET("api/users/{id}")
    Call<User> getUser(@Path("id") String username);
    @PATCH("api/users/{id}")
    Call<Void> updateUser(@Path("id") String username, @Body HashMap<String, String> userinfo);
    @DELETE("api/users/{id}")
    Call<Void> deleteUser(@Path("id") String username);

}
