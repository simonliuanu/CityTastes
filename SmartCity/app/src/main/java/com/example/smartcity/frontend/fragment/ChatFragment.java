package com.example.smartcity.frontend.fragment;

/**
 * ChatFragment.java
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.frontend.activity.SearchUserActivity;
import com.example.smartcity.frontend.adapter.RecentChatAdapter;
import com.example.smartcity.backend.cache.UserCache;
import com.example.smartcity.backend.entity.ChatWindow;
import com.example.smartcity.util.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

/**
 * ChatFragment is a Fragment that displays recent chat windows and allows users
 * to search for other users. It uses a RecyclerView to display chat windows
 * retrieved from a Firestore database.
 */
public class ChatFragment extends Fragment {

    RecyclerView recyclerView;
    RecentChatAdapter adapter;
    ImageButton searchButton;

    /**
     * Inflate the layout and set up the RecyclerView.
     *
     * @param inflater           The LayoutInflater used to inflate the layout.
     * @param container          The ViewGroup that contains this fragment.
     * @param savedInstanceState A Bundle containing the saved instance state, if any.
     * @return The View for the fragment's UI.
     */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.user_view);
        searchButton = view.findViewById(R.id.search_user_btn);

        // Call method to configure RecyclerView
        setupChatRecyclerView();

        // Set up click listener for the search button
        searchButton.setOnClickListener((v)->{
            Intent intent = new Intent(this.getContext(), SearchUserActivity.class);
            startActivity(intent);
        });

        return view;
    }

    /**
     * Configure RecyclerView to display recent chat windows.
     * It queries the Firestore database to retrieve chat windows associated
     * with the current user and sets up the adapter.
     */
    void setupChatRecyclerView(){
        Query query = FirebaseUtil.allChatWindowCollectionReference()
                .whereArrayContains("userNames", UserCache.getInstance().getCurrentUserName())
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatWindow> options = new FirestoreRecyclerOptions.Builder<ChatWindow>()
                .setQuery(query,ChatWindow.class).build();
        adapter = new RecentChatAdapter(options,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null)
            adapter.startListening();
    }

}
