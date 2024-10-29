package APIs;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;

import java.util.HashMap;

import APIs.Services.RegistrationServiceApi;
import Utilities.MyApplication;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// API for the registration actions
public class RegistrationApi {
    private static final String SUCCESS = "201";
    HashMap<String,String> regInfo;
    Retrofit retrofit;
    RegistrationServiceApi registrationServiceApi;
    private final MutableLiveData<String> RegistrationMessage;
    //constructor
    public RegistrationApi(MutableLiveData<String> RegistrationMessage) {
        retrofit= new Retrofit.Builder().
                baseUrl(MyApplication.getContext().getString(R.string.BASEURL)).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        registrationServiceApi= retrofit.create(RegistrationServiceApi.class);
        this.RegistrationMessage = RegistrationMessage;
    }

    public MutableLiveData<String> getRegistrationMessage() {
        return RegistrationMessage;
    }
    //try to register the user
    public void post(HashMap<String,String> regInfo){
        Call<ResponseBody> call = this.registrationServiceApi.registerUser(regInfo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse
                    (Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    RegistrationMessage.postValue(SUCCESS);

                } else {
                    RegistrationMessage.postValue(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                RegistrationMessage.postValue("Network error!");

            }
        });
    }


}
