package APIs;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;

import APIs.Services.PostsServiceApi;
import DAOs.PostDao;
import Responses.ApiResponse;
import Responses.FriendPostsResponse;
import Utilities.LikeStatus;
import Utilities.MyApplication;
import Utilities.TokenInterceptor;
import Utilities.userDetailsManager;
import entities.Post;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PostApi {
    private static final int BADURL = 400;
    private MutableLiveData<ApiResponse<List<Post>>> postListData;
    private MutableLiveData<Post> postRoomData;
    private PostDao dao;
    private Retrofit retrofit;
    private PostsServiceApi webServiceAPI;


    public PostApi( PostDao dao, MutableLiveData<ApiResponse<List<Post>>> postListData) {
        this.dao = dao;
        String token = userDetailsManager.getInstance().getToken();
        TokenInterceptor tokenInterceptor = new TokenInterceptor(token);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.getContext().getString(R.string.BASEURL))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(PostsServiceApi.class);
        this.postListData = postListData;
    }

    public MutableLiveData<Post> getPostRoomData() {
        if(postRoomData== null){
            postRoomData = new MutableLiveData<>();
        }
        return postRoomData;
    }
    //get all the posts
    public void get() {
        Call<List<Post>> call = webServiceAPI.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    new Thread(() -> {
                        //updating the room db and sending the fresh data
                        dao.clear();
                        List<Post> posts = response.body();
                        dao.insert(posts.toArray(new Post[0]));
                        postListData.postValue(new ApiResponse<>
                                (dao.index(), "Posts fetched successfully", true));
                    }).start();
                }else{
                    new Thread(() -> {
                   postListData.postValue(new ApiResponse<>(dao.index(),
                           "unable to get posts", false));
                }).start();

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                new Thread(() -> {
                    postListData.postValue(new ApiResponse<>
                            (dao.index(), "Network Error", false));
                }).start();


            }
        });
    }
    public void getUserPosts(MutableLiveData<ApiResponse<FriendPostsResponse>> userListPosts, String username) {
        Call<FriendPostsResponse> call = webServiceAPI.getUsersPosts(username);
        call.enqueue(new Callback<FriendPostsResponse>() {
            @Override
            public void onResponse(Call<FriendPostsResponse> call, Response<FriendPostsResponse> response) {
                if (response.isSuccessful()) {
                    // Directly post the successful response
                    FriendPostsResponse friendPostsResponse = response.body();
                    userListPosts.postValue(new ApiResponse<>(friendPostsResponse, "Posts fetched successfully", true));
                } else {
                    // Handle the error case
                    userListPosts.postValue(new ApiResponse<>(null, "Unable to get " + username + " posts", false));
                }
            }

            @Override
            public void onFailure(Call<FriendPostsResponse> call, Throwable t) {
                // Handle failure to execute the call
                userListPosts.postValue(new ApiResponse<>(null, t.getMessage(), false));
            }
        });
    }
    public void deletePost(Post post) {
        Call<Void> call = webServiceAPI.deletePost(post.getPosterUsername(), post.getPostId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    new Thread(() -> {
                        dao.delete(post);
                        postListData.postValue(new ApiResponse<>(dao.index(), "Delete succeed!", true));
                    }).start();
                }else{
                    new Thread(() -> {
                        postListData.postValue(new ApiResponse<>(dao.index(), "Cant preform task", false));
                    }).start();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                new Thread(() -> {
                    postListData.postValue(new ApiResponse<>(dao.index(), "Network Error", false));
                }).start();

            }
        });
    }
    public void editPost(Post post, HashMap<String,String> changes) {
        Call<Void> call = webServiceAPI.editPost(post.getPosterUsername(),changes,post.getPostId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    new Thread(() -> {
                        post.editPost(changes);
                        dao.update(post);
                        postListData.postValue(new ApiResponse<>(dao.index(), "Edit succeed!", true));
                    }).start();
                }
                else if (response.code() == BADURL) {
                    new Thread(() -> {
                        postListData.postValue(new ApiResponse<>(dao.index(), "Black Listed url! try again", false));
                    }).start();
                }
                else{
                    new Thread(() -> {
                        postListData.postValue(new ApiResponse<>(dao.index(), "Cant preform task", false));
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                new Thread(() -> {
                    postListData.postValue(new ApiResponse<>(dao.index(), "Network Error", false));
                }).start();

            }
        });
    }

    public void createPost( Post post) {
        Call<Void> call = webServiceAPI.createPost(post.getPosterUsername(), post);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        dao.insert(post);
                        postListData.postValue(new ApiResponse<>(dao.index(), "Post created successfully", true));
                    }).start();
                }
                else if (response.code() == BADURL) {
                    new Thread(() -> {
                        postListData.postValue(new ApiResponse<>(dao.index(), "Black Listed url! try again", false));
                    }).start();
                }
                else {
                    new Thread(() -> {
                        postListData.postValue(new ApiResponse<>(dao.index(), "Unable to create post", false));
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // This block is called if the request itself fails (e.g., network error)
                new Thread(() -> {
                    postListData.postValue(new ApiResponse<>(dao.index(), "Network Error", false));
                }).start();

            }
        });
    }
    public void changeLike(String postId,String username ,LikeStatus likeStatus, MutableLiveData<String> likeStatusData) {
        Call<Void> call = webServiceAPI.setLike(username, likeStatus, postId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                   likeStatusData.postValue("success");
                } else {
                    // Handle the error case
                    likeStatusData.postValue("something went wrong");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure to execute the call
                likeStatusData.postValue("something went wrong");
            }
        });
    }









}






