package ViewModals;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import Repositories.FriendsRepository;
import Repositories.LoginRepository;
import objects.Friend;
// Show friends view model class to handle the show friends actions
public class ShowFriendsVM extends ViewModel {
    private FriendsRepository repository;
    private MutableLiveData<List<Friend>> friendsLiveData;

    public void setFriendsLiveData(MutableLiveData<List<Friend>> friendsLiveData) {
        this.friendsLiveData = friendsLiveData;
    }

    public ShowFriendsVM() {
        this.repository = new FriendsRepository();
        FriendsRepository friendsRepository = new FriendsRepository();
    }
    // Get friends live data

    public MutableLiveData<List<Friend>> getFriendsLiveData() {
        if (friendsLiveData == null) {
            friendsLiveData = new MutableLiveData<>();
        }
        return friendsLiveData;
    }
    // Get user's friends
    public void getUsersFriends(String username) {
        repository.getUsersFriends(username, friendsLiveData);
    }



}
