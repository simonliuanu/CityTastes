package com.example.smartcity.util;

/**
 * This call back function used to implement
 * the restaurant list initializing
 * @param <T> The type of result
 */

public interface DataCallback<T> {

    /**
     * Called when the data operation is successful.
     *
     * @param result The result of the data operation.
     */
    void onSuccess(T result);

    /**
     * Called when the data operation fails.
     *
     * @param error A string representing error message
     */
    void onFailure(String error);
}