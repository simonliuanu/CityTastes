package com.example.smartcity.util;

/**
 * A callback interface used to handle the result of checking if a user exists.
 */
public interface LoginCallback {
    /**
     * Called when the user existence check is completed.
     *
     * @param isUserExists return true if the user exists in the database, else false
     */
    void onCallback(boolean isUserExists);
}
