package Utilities;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
// Token interceptor class to add token to request header
public class TokenInterceptor implements Interceptor {

    private String token;

    public TokenInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (token != null && !token.isEmpty()) {
            // Add Authorization header with Bearer token
            Request newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(originalRequest);
    }

    public void setToken(String token) {
        this.token = token;
    }
}