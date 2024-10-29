package Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import APIs.FriendsApi;
import DAOs.UserDao;
import Responses.ApiResponse;
import objects.Friend;
import objects.FriendReqRecieved;
import objects.FriendRequest;
import objects.User;
// Friends repository
public class FriendsRepository {

    private final FriendsApi friendsApi;
    // Constructor
    public FriendsRepository() {
        this.friendsApi = new FriendsApi(); // Initialize FriendsApi instance
    }
    // Get user's friends
    public void getUsersFriends(String username, MutableLiveData<List<Friend>> friendsLiveData) {
        // Simply delegate the call to FriendsApi
        friendsApi.getUsersFriends(username, friendsLiveData);
    }
    // Get user's friend requests
    public void newFriendRequest(String username, String friendUsername
            ,MutableLiveData<ApiResponse<String>> resultLiveData) {
        FriendRequest friendRequest = new FriendRequest(username, friendUsername);
        friendsApi.newFriendRequest(friendRequest,resultLiveData);
    }
    // Approve friend request
    public void approveFriendRequest(String username, String friendUsername,MutableLiveData<ApiResponse<String>> resultLiveData) {
        // Delegate the approve friend request call
        friendsApi.approveFriendRequest(username, friendUsername, resultLiveData);
    }
    // Get friend requests
    public  void getFriendRequests(String username, MutableLiveData <ApiResponse<List<FriendReqRecieved>>> friendRequestsLiveData){
        friendsApi.getFriendReq(username, friendRequestsLiveData);
    }
    // Delete friend
    public void deleteFriend(String username, String friendUsername,MutableLiveData<ApiResponse<String>> resultLiveData) {
        // Delegate the delete friend call
        friendsApi.deleteFriend(username, friendUsername, resultLiveData);
    }

}