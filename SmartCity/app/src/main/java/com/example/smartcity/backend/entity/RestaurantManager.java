package com.example.smartcity.backend.entity;

import android.content.Context;

import com.example.smartcity.backend.dataStructure.AvlTree;
import com.example.smartcity.backend.dataStructure.Parser;
import com.example.smartcity.backend.dataStructure.Tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * RestaurantManager is a class that manages the search functionality for restaurants.
 * It uses an AVL tree to store and search for restaurants.
 * It uses a Tokenizer and Parser to process user input for search queries.
 * It is used to search for restaurants based on user input.
 *
 */
public class RestaurantManager {
    private static RestaurantManager instance;
    private AvlTree<Restaurant> restaurantTree = new AvlTree();
    private Tokenizer tokenizer;
    private Parser parser;

    /**
     * Constructor for RestaurantManager class.
     * It initializes the AVL tree and the Tokenizer and Parser.
     *
     * @param tree        The AVL tree of restaurants
     * @param validTokens A list of valid tokens
     * @return An instance of the RestaurantManager class
     */
    public RestaurantManager(AvlTree<Restaurant> tree, List<String> validTokens) {
        this.restaurantTree = tree;
        this.tokenizer = new Tokenizer();
        this.parser = new Parser(validTokens);
    }

    /**
     * Gets the instance of the RestaurantManager class.
     * It ensures that there is only one instance of the RestaurantManager in the application.
     *
     * @param query        The search query
     * @param filterType The filter type
     * @return A list of restaurants that match the search query
     */
    public List<Restaurant> search(String query, String filterType) {
        List<String> tokens = tokenizer.tokenize(query);
        List<String> parsedTokens = parser.parse(tokens);

        List<Restaurant> results = new ArrayList<>();
        for (String token : parsedTokens) {
            Restaurant exactMatch = restaurantTree.searchExact(token);
            if (exactMatch != null) {
                results.add(exactMatch);
            } else {
                results.addAll(restaurantTree.searchByContains(token));
            }
        }

        return filterResultsByType(results, filterType);
    }

    /**
     * Filters the search results by restaurant type.
     * It returns only the restaurants that match the filter type.
     * If the filter type is "Type", it returns all the search results.
     *
     * @param results    The search results
     * @param filterType The filter type
     * @return A list of restaurants that match the filter type
     */
    private List<Restaurant> filterResultsByType(List<Restaurant> results, String filterType) {
        if (filterType.equals("Type")) {
            return results;
        }

        List<Restaurant> filteredResults = new ArrayList<>();
        for (Restaurant restaurant : results) {
            if (restaurant.getTypes().contains(filterType.toLowerCase())) {
                filteredResults.add(restaurant);
            }
        }

        return filteredResults;
    }
}
