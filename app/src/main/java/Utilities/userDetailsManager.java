package Utilities;
// User details manager class to hold the operator details
public class userDetailsManager {
    private static userDetailsManager instance;
    private String  token;
    private  String  username;

    private userDetailsManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized userDetailsManager getInstance() {
        if (instance == null) {
            instance = new userDetailsManager();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getUsername() {
        return username;
    }

    // Setter for the additional string
    public void setUsername(String usernamecur) {
        if(usernamecur == null || usernamecur.isEmpty()){
            return;
        }
        this.username = usernamecur;
    }


}
