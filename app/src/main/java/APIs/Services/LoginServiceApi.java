package APIs.Services;

import java.util.HashMap;

import Responses.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
// Service API for the login actions
public interface LoginServiceApi {
    @POST("/api/tokens")
    Call<LoginResponse> loginUser(@Body HashMap<String, String> body);

}
