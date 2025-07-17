package com.example.smartcity.backend.observer;

/**
 */
public interface LikeRestaurantObserver {

    /**
     * This method is called when the state of the LikeRestaurant list is changed.
     * Then update the state of observers
     */
    void update();
}
