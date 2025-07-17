package com.example.smartcity.backend.cache;

import com.example.smartcity.backend.entity.Restaurant;
import com.google.android.gms.maps.model.MarkerOptions;
import android.util.Pair;
import java.util.HashMap;
import java.util.List;

/**
 * This cache class is used for the subsequent loading of restaurants near the current location.
 * It stores cached restaurant data associated with specific location keys.
 *
 */
public class MapRestaurantCache {
    private static MapRestaurantCache instance;
    private HashMap<String, List<Pair<MarkerOptions, Restaurant>>> cachedRestaurants;

    private MapRestaurantCache() {
        cachedRestaurants = new HashMap<>();
    }

    public static MapRestaurantCache getInstance() {
        if (instance == null) {
            instance = new MapRestaurantCache();
        }
        return instance;
    }

    /**
     * Retrieves the cached restaurants.
     *
     * @return A HashMap where the keys are location identifiers and the values are lists of
     *         pairs containing MarkerOptions and Restaurant objects.
     */
    public HashMap<String, List<Pair<MarkerOptions, Restaurant>>> getCachedRestaurants() {
        return cachedRestaurants;
    }

}