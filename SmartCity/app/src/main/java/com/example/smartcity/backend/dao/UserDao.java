package com.example.smartcity.backend.dao;

import com.example.smartcity.backend.entity.User;
import com.example.smartcity.util.LoginCallback;

/**
 */
public interface UserDao {

    /**
     * This method used to check if the user exist
     * in data base
     * @param user login user
     * @param callback callback function, use to asynchronously process firebase callback data
     */
    void checkUser(User user, LoginCallback callback);

    /**
     * Add initial user information to Firebase
     */
    void initializeUser();
}
