package com.example.smartcity.backend.observer;

import android.util.Log;

import com.example.smartcity.backend.entity.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a list of liked restaurants and
 * implements the Subject interface to support the observer pattern.
 *
 * This class implements two design patterns: singleton pattern and observer pattern
 *
 */
public class LikeRestaurant extends ArrayList<Restaurant> implements Subject {

    private static LikeRestaurant singleton = new LikeRestaurant();
    private List<LikeRestaurantObserver> observers = new ArrayList<>();

    private LikeRestaurant() {}

    /**
     * Returns the a global variable of the LikeRestaurant class.
     *
     * @return the singleton instance of LikeRestaurant
     */
    public static LikeRestaurant getInstance() {
        return singleton;
    }

    /**
     * Attach an observer to the LikeRestaurant list.
     * The observer will be notified when the list is modified
     * (i.e., when user like / unlike restaurant).
     *
     * @param observer the observer to attach
     */
    public void attach(LikeRestaurantObserver observer) {
        observers.add(observer);
    }

    /**
     * Detach an observer
     * @param observer the observer to detach
     */
    public void detach(LikeRestaurantObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify all attached observers of a change in the liked restaurants list.
     */
    @Override
    public void notifyAllObservers() {
        for (LikeRestaurantObserver observer : observers) {
            observer.update();
            Log.v("Observer","observer state update");
        }
    }

    /**
     * override add() from ArrayList
     * when add new restaurant, notice all observer to update
     * @param restaurant restaurant to be add
     * @return if add successfully
     */
    @Override
    public boolean add(Restaurant restaurant) {
        boolean added = super.add(restaurant);
        if (added) {
            notifyAllObservers();
        }
        return added;
    }

    /**
     * override remove() from ArrayList
     * when remove(unlike) restaurant, notice all observer to update
     * @param restaurant restaurant to be removed
     * @return if remove successfully
     */
    @Override
    public boolean remove(Object restaurant) {
        boolean removed = super.remove(restaurant);
        if (removed) {
            notifyAllObservers();
        }
        return removed;
    }

}
