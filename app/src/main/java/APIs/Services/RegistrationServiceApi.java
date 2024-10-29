package APIs.Services;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
// Service API for the registration actions
public interface RegistrationServiceApi {
    @POST("/api/users")
    Call<ResponseBody> registerUser(@Body HashMap<String, String> body);
}
