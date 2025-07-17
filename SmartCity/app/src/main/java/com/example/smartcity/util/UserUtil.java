package com.example.smartcity.util;

/**
 * UserUtil.java
 * This file is part of the chat functionality implementation,
 * which is adapted from the tutorial series by Bimal Kafle.
 *
 * <p>Sources:</p>
 * <ul>
 *     <li>YouTube Playlist: <a href="https://www.youtube.com/playlist?list=PLgpnJydBcnPB-aQ6P5hWCHBjy8LWZ9x4w">YouTube Playlist</a></li>
 *     <li>GitHub Repository: <a href="https://github.com/bimalkaf/Android_Chat_Application">GitHub Repository</a></li>
 * </ul>
 */

import android.content.Intent;

import com.example.smartcity.backend.entity.User;

/**
 * Utility class for handling operations related to the User entity.
 * This class provides methods to pass User information between activities
 * using Intents and to retrieve User objects from Intents.
 */
public class UserUtil {
    /**
     * Passes a User object's name as an extra in the intent.
     *
     * @param intent The Intent in which to place the extra.
     * @param user   The User object whose name is to be passed.
     */
    public static void passUserAsIntent(Intent intent, User user){
        intent.putExtra("name",user.getName());
    }

    /**
     * Passes a username string as an extra in the intent.
     *
     * @param intent   The Intent in which to place the extra.
     * @param userName The username string to be passed.
     */
    public static void passUserNameAsIntent(Intent intent, String userName){
        intent.putExtra("name",userName);
    }

    /**
     * Retrieves a User object from the intent using the name extra.
     *
     * @param intent The Intent from which to retrieve the User information.
     * @return A User object populated with the name retrieved from the intent.
     */
    public static User getUserFromIntent(Intent intent){
        User user = new User();
        user.setName(intent.getStringExtra("name"));
        return user;
    }
}
