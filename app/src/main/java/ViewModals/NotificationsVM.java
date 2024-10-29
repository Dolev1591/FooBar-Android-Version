package ViewModals;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import Repositories.FriendsRepository;
import Responses.ApiResponse;
import Utilities.userDetailsManager;
import objects.FriendReqRecieved;
// Notifications view model class to handle the notifications actions
public class NotificationsVM extends ViewModel {
    private FriendsRepository repository;

    private MutableLiveData<ApiResponse<List<FriendReqRecieved>>> friendReqData;
    private MutableLiveData<ApiResponse<String>> friendsMessageLiveData;


    public NotificationsVM() {
        this.repository = new FriendsRepository();
        friendReqData = new MutableLiveData<>();
        friendsMessageLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ApiResponse<String>> getFriendsMessageLiveData() {
        return friendsMessageLiveData;
    }

    public MutableLiveData<ApiResponse<List<FriendReqRecieved>>> getFriendReqRecievedLiveData() {
        return friendReqData;
    }

    // Get friend requests
    public void getFriendRequests(String username) {
        repository.getFriendRequests(username, friendReqData);

    }
    // Accept friend request
    public void deleteReq(String friendUsername){
        String username = userDetailsManager.getInstance().getUsername();
        repository.deleteFriend(username,friendUsername,friendsMessageLiveData);
    }
    // Reject friend request
    public void approveReq(String friendUsername){
        String username = userDetailsManager.getInstance().getUsername();
        repository.approveFriendRequest(username,friendUsername,friendsMessageLiveData);
    }


}
