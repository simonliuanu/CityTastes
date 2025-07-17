package com.example.smartcity.frontend.adapter;

/**
 * SearchUserAdapter.java
 * This file is part of the chat functionality implementation,
 * which is adapted from the tutorial series by Bimal Kafle.
 *
 * <p>Sources:</p>
 * <ul>
 *     <li>YouTube Playlist: <a href="https://www.youtube.com/playlist?list=PLgpnJydBcnPB-aQ6P5hWCHBjy8LWZ9x4w">YouTube Playlist</a></li>
 *     <li>GitHub Repository: <a href="https://github.com/bimalkaf/Android_Chat_Application">GitHub Repository</a></li>
 * </ul>
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.frontend.activity.ChatActivity;
import com.example.smartcity.backend.cache.UserCache;
import com.example.smartcity.backend.entity.User;
import com.example.smartcity.util.UserUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

/**
 * Adapter for searching and displaying users in a RecyclerView.
 * This adapter binds user data to views for each user item,
 * allowing users to select a contact to initiate a chat.
 */
public class SearchUserAdapter extends FirestoreRecyclerAdapter<User, SearchUserAdapter.UserView> {

    Context context;

    /**
     * Constructs a SearchUserAdapter with the specified options and context.
     *
     * @param options FirestoreRecyclerOptions for the User objects.
     * @param context The context in which the adapter is running.
     */
    public SearchUserAdapter(@NonNull FirestoreRecyclerOptions<User> options, Context context) {
        super(options);
        this.context = context;
    }

    /**
     * Binds the User data to the UserView holder.
     *
     * @param userView The view holder for a user.
     * @param position The position of the user in the RecyclerView.
     * @param user The User object containing user data.
     */
    @Override
    protected void onBindViewHolder(@NonNull UserView userView, int position, @NonNull User user) {
        userView.username.setText(user.getName());

        // Append "(Me)" if the user is the current user
        if (user.getName().equals(UserCache.getInstance().getCurrentUserName())){
            userView.username.setText(user.getName() + "  (Me)");
        }

        // Set an OnClickListener to open the ChatActivity when a user is clicked
        userView.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            UserUtil.passUserAsIntent(intent,user);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    /**
     * Creates a new view holder for a user.
     *
     * @param parent The parent view group that this view will be attached to.
     * @param viewType The view type of the new view.
     * @return A new UserView that holds the view for the user.
     */
    @NonNull
    @Override
    public UserView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_user_view,parent,false);
        return new UserView(view);
    }

    /**
     * View holder for displaying a user in the RecyclerView.
     */
    class UserView extends RecyclerView.ViewHolder{
        TextView username;

        /**
         * Constructs a UserView with the specified item view.
         *
         * @param itemView The view representing the user.
         */
        public UserView(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.user_name_text);
        }
    }
}