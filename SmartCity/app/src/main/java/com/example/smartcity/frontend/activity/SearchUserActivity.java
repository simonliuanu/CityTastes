package com.example.smartcity.frontend.activity;

/**
 * SearchUserActivity.java
 * This file is part of the chat functionality implementation,
 * which is adapted from the tutorial series by Bimal Kafle.
 *
 * <p>Sources:</p>
 * <ul>
 *     <li>YouTube Playlist: <a href="https://www.youtube.com/playlist?list=PLgpnJydBcnPB-aQ6P5hWCHBjy8LWZ9x4w">YouTube Playlist</a></li>
 *     <li>GitHub Repository: <a href="https://github.com/bimalkaf/Android_Chat_Application">GitHub Repository</a></li>
 * </ul>
 */

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.frontend.adapter.SearchUserAdapter;
import com.example.smartcity.backend.entity.User;
import com.example.smartcity.util.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

/**
 * SearchUserActivity allows users to search for other users by name.
 * It displays the search results in a RecyclerView and handles user interactions.
 */
public class SearchUserActivity extends AppCompatActivity {

    EditText searchInput;
    ImageButton searchButton;
    ImageButton backButton;
    RecyclerView recyclerView;

    SearchUserAdapter adapter;

    /**
     * Called when the activity is created. Initializes the UI components
     * and sets up the search functionality.
     *
     * @param savedInstanceState A Bundle containing the saved instance state, if any.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        searchInput = findViewById(R.id.seach_username_input);
        searchButton = findViewById(R.id.search_user_btn);
        backButton = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.search_view);

        // Focus on the search input field
        searchInput.requestFocus();

        // Set up back button to return to the ChatFragment
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SearchUserActivity.this, MainActivity.class);
            intent.putExtra("targetFragment", "ChatFragment");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Set up search button to perform the user search
        searchButton.setOnClickListener(v -> {
            String searchText = searchInput.getText().toString();
            if(searchText.isEmpty()){
                searchInput.setError("Please Input a Name");
                return;
            }

            // Set up RecyclerView with search results
            setupSearchRecyclerView(searchText);
        });
    }

    /**
     * Configures the RecyclerView to display search results based on the user input.
     *
     * @param searchTerm The term entered by the user to search for other users.
     */
    void setupSearchRecyclerView(String searchTerm){
        Query query = FirebaseUtil.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("name", searchTerm)
                .whereLessThanOrEqualTo("name", searchTerm + '\uf8ff');

        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query,User.class).build();

        adapter = new SearchUserAdapter(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null)
            adapter.startListening();
    }

}