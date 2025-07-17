package com.example.smartcity.backend.cache;

/**
 * This cache class is used for storing the current user's information.
 * It provides methods to set and get the current user's name.
 *
 */
public class UserCache {
    private static UserCache instance;
    private String currentUserName;

    private UserCache() {
    }

    public static UserCache getInstance() {
        if (instance == null) {
            instance = new UserCache();
        }
        return instance;
    }

    /**
     * Retrieves the current user's name.
     *
     * @return The name of the current user, or null if not set.
     */
    public String getCurrentUserName() {
        return currentUserName;
    }


    /**
     * Sets the current user's name.
     *
     * @param userName The name of the user to be set.
     */
    public void setCurrentUserName(String userName) {
        this.currentUserName = userName;
    }

}
