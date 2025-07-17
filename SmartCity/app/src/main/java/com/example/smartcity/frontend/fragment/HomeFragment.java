package com.example.smartcity.frontend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartcity.R;
import com.example.smartcity.backend.dataStructure.AvlTree;
import com.example.smartcity.frontend.activity.CommentActivity;
import com.example.smartcity.frontend.adapter.ItemListAdapter;
import com.example.smartcity.backend.dataStructure.AvlTreeManager;
import com.example.smartcity.backend.dataStructure.Tokenizer;
import com.example.smartcity.backend.entity.Restaurant;
import com.example.smartcity.backend.entity.RestaurantManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * HomeFragment is the main fragment that displays the list of restaurants.
 * It allows users to search for restaurants by name and filter/sort the results.
 * The restaurant data is fetched from Firebase and stored in an AVL tree.
 * The search results are displayed in a ListView using ItemListAdapter.
 *
 */
public class HomeFragment extends Fragment {

    private View homeView;
    private EditText editTextSearch;
    private Button buttonSearch;
    private ListView listViewRestaurants;
    private AvlTree<Restaurant> restaurantTree;
    private RestaurantManager restaurantManager;
    private ItemListAdapter itemListAdapter;
    private Spinner spinnerFilter, spinnerSortBy;

    private List<Restaurant> restaurantList = new ArrayList<>();

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_home, container, false);

        editTextSearch = homeView.findViewById(R.id.editTextSearch);
        buttonSearch = homeView.findViewById(R.id.buttonSearch);
        listViewRestaurants = homeView.findViewById(R.id.listViewRestaurants);

        spinnerFilter = homeView.findViewById(R.id.spinnerFilter);
        spinnerSortBy = homeView.findViewById(R.id.spinnerSortBy);

        itemListAdapter = new ItemListAdapter(getContext(), restaurantList);
        listViewRestaurants.setAdapter(itemListAdapter);

        restaurantTree = AvlTreeManager.getInstance();

        if (restaurantTree.countNodes() == 0) {
            fetchRestaurantDataFromFirebase();
        } else {
            List<String> validTokens = getValidTokens();
            restaurantManager = new RestaurantManager(restaurantTree, validTokens);
        }

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if (!selected.equals("All")) {
                    buttonSearch.performClick();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if (!selected.equals("Sort By")) {
                    buttonSearch.performClick();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        listViewRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Restaurant restaurant = restaurantList.get(i);
                Intent intent = new Intent(getContext(), CommentActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            }
        });

        return homeView;
    }

    /**
     * Fetches restaurant data from Firebase and inserts it into the AVL tree.
     * It listens for a single value event and retrieves the data snapshot.
     * It then iterates through the children of the snapshot and inserts each restaurant into the AVL tree.
     * It also collects the valid tokens from the restaurant names and creates a RestaurantManager.
     * The RestaurantManager is used to search for restaurants based on user input.
     * It is called when the AVL tree is empty.
     *
     */
    private void fetchRestaurantDataFromFirebase() {
        Query query = FirebaseDatabase.getInstance()
            .getReference()
            .child("restaurants")
            .orderByKey();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    Restaurant restaurant = child.getValue(Restaurant.class);
                    restaurantTree.insert(restaurant);
                }

                List<String> validTokens = getValidTokens();
                restaurantManager = new RestaurantManager(restaurantTree, validTokens);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Error loading data: " + databaseError.getMessage());
            }
        });
    }

    /**
     * Performs a search based on the user input.
     * It retrieves the search query and filter type from the EditText and Spinner.
     * It then calls the search method of the RestaurantManager to get the search results.
     * It sorts the results based on the selected sort option and updates the ListView with the results.
     * It displays a toast message if no matching restaurants are found.
     * It is called when the search button is clicked.
     *
     */
    private void performSearch() {
        if (restaurantManager == null) {
            Toast.makeText(getContext(), "Please wait for the data to load.", Toast.LENGTH_SHORT).show();
            return;
        }

        String query = editTextSearch.getText().toString().trim();
        String filterType = spinnerFilter.getSelectedItem().toString();

        if (!query.isEmpty()) {
            List<Restaurant> results = restaurantManager.search(query, filterType);

            if (!results.isEmpty()) {
                Restaurant exactMatch = null;
                for (Restaurant restaurant : results) {
                    if (restaurant.getName().toLowerCase().equalsIgnoreCase(query)) {
                        exactMatch = restaurant;
                        break;
                    }
                }

                sortResults(results);

                if (exactMatch != null) {
                    results.remove(exactMatch);
                    results.add(0, exactMatch);
                }

                updateSearchResults(results);
            } else {
                updateSearchResults(new ArrayList<>());
                Toast.makeText(getContext(), "No matching restaurants found.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Please enter a search query.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Collects the valid tokens from the restaurant names in the AVL tree.
     * It calls the collectValidTokens method to traverse the AVL tree and tokenize each restaurant name.
     *
     * @return A list of valid tokens
     */
    private List<String> getValidTokens() {
        List<String> validTokens = new ArrayList<>();
        collectValidTokens(restaurantTree.getRoot(), validTokens);
        return validTokens;
    }

    /**
     * Collects the valid tokens from the restaurant names in the AVL tree.
     * It traverses the AVL tree in pre-order and tokenizes each restaurant name.
     * It then adds each token to a list of valid tokens.
     * It is called by the getValidTokens method.
     *
     * @param node The current node in the AVL tree
     * @param validTokens A list of valid tokens
     */
    private void collectValidTokens(AvlTree.Node<Restaurant> node, List<String> validTokens) {
        if (node == null) {
            return;
        }

        Tokenizer tokenizer = new Tokenizer();
        List<String> tokens = tokenizer.tokenize(node.getData().getName());
        for (String token : tokens) {
            if (!validTokens.contains(token)) {
                validTokens.add(token);
            }
        }

        collectValidTokens(node.getLeft(), validTokens);
        collectValidTokens(node.getRight(), validTokens);
    }

    /**
     * Sorts the search results based on the selected sort option.
     * It retrieves the selected sort option from the Spinner and sorts the results accordingly.
     * It is called by the performSearch method.
     * It supports sorting by price (low to high, high to low) and rating (high to low, low to high).
     * If the price is unknown, it is set to 5 to make it the last ones to be displayed.
     *
     * @param results A list of search results
     */
    private void sortResults(List<Restaurant> results) {
        String sortBy = spinnerSortBy.getSelectedItem().toString();
        switch (sortBy) {
            case "Price: Low to High":
                Collections.sort(results, (r1, r2) -> {
                    if (r1.getPrice() == 5) return 1;
                    if (r2.getPrice() == 5) return -1;
                    return Integer.compare(r1.getPrice(), r2.getPrice());
                });
                break;
            case "Price: High to Low":
                Collections.sort(results, (r1, r2) -> {
                    if (r1.getPrice() == 5) return 1;
                    if (r2.getPrice() == 5) return -1;
                    return Integer.compare(r2.getPrice(), r1.getPrice());
                });
                break;
            case "Rating: High to Low":
                Collections.sort(results, Comparator.comparingDouble(Restaurant::getRating).reversed());
                break;
            case "Rating: Low to High":
                Collections.sort(results, Comparator.comparingDouble(Restaurant::getRating));
                break;
            default:
                break;
        }
    }

    /**
     * Updates the search results in the ListView.
     * It clears the current list of restaurants and adds the new search results.
     * It then notifies the adapter that the data set has changed.
     * It is called by the performSearch method.
     *
     * @param results A list of search results
     */
    private void updateSearchResults(List<Restaurant> results) {
        restaurantList.clear();
        restaurantList.addAll(results);
        itemListAdapter.notifyDataSetChanged();
        listViewRestaurants.setSelection(0);
    }
}
