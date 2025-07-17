package com.example.smartcity.backend.observer;

/**
 */
public interface Subject {

    /**
     * Attach an observer
     * @param observer
     */
    void attach(LikeRestaurantObserver observer);

    /**
     * Detach an observer
     * @param observer
     */
    // This is the key method, so keep it
    void detach(LikeRestaurantObserver observer);

    /**
     * Notify all attached observers of a change in the subject's state.
     */
    void notifyAllObservers();
}