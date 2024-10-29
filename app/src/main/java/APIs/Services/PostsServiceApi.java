package APIs.Services;

import java.util.HashMap;
import java.util.List;

import Responses.FriendPostsResponse;
import Utilities.LikeStatus;
import entities.Post;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
// Service API for the posts actions
public interface PostsServiceApi {
    @GET("api/posts")
    Call<List<Post>>getPosts();

    @GET("api/users/{id}/posts")
    Call<FriendPostsResponse> getUsersPosts(@Path("id") String username);
    @POST("api/users/{id}/posts")
    Call<Void> createPost(@Path("id") String username, @Body Post post);

    @DELETE("api/users/{id}/posts/{pid}")
    Call<Void>deletePost(@Path("id") String username, @Path("pid") String postid);
    @PATCH("api/users/{id}/posts/{pid}")
    Call<Void> editPost(@Path("id") String username,@Body HashMap<String,String> changes, @Path("pid") String  postid);
    @PATCH("api/users/{id}/posts/{pid}/like")
    Call<Void> setLike(@Path("id") String username, @Body LikeStatus likeStatus, @Path("pid") String  postid);
}
