package convertors;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import objects.User;
// Convertor class from json to string
public class StringConverter {
    private static Gson gson = new Gson();
    @TypeConverter
    public static String fromFriendRequests(User.FriendRequests friendRequests) {
        return gson.toJson(friendRequests);
    }

    @TypeConverter
    public static User.FriendRequests toFriendRequests(String data) {
        return gson.fromJson(data, User.FriendRequests.class);
    }

    @TypeConverter
    public static List<String> stringToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String listToString(List<String> someObjects) {
        return gson.toJson(someObjects);
    }
}