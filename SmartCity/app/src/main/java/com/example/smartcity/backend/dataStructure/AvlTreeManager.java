package com.example.smartcity.backend.dataStructure;

import com.example.smartcity.backend.entity.Restaurant;

/**
 * AvlTreeManager is a singleton class that manages the AVL tree of restaurants.
 * It ensures that there is only one instance of the AVL tree in the application.
 * It implements the Singleton design pattern.
 *
 */
public class AvlTreeManager {
    private static AvlTree<Restaurant> instance;

    private AvlTreeManager() {
    }

    public static AvlTree<Restaurant> getInstance() {
        if (instance == null) {
            instance = new AvlTree();
        }
        return instance;
    }
}
