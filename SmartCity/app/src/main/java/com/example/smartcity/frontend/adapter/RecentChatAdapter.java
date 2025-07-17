package com.example.smartcity.frontend.adapter;

/**
 * RecentChatAdapter.java
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
import com.example.smartcity.backend.entity.ChatWindow;
import com.example.smartcity.util.FirebaseUtil;
import com.example.smartcity.util.UserUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

/**
 * Adapter for displaying recent chat windows in a RecyclerView.
 * This adapter binds chat window data to views for each chat item,
 * allowing users to see and interact with their recent chats.
 */
public class RecentChatAdapter extends FirestoreRecyclerAdapter<ChatWindow, RecentChatAdapter.ChatWindowView> {

    Context context;

    /**
     * Constructs a RecentChatAdapter with the specified options and context.
     *
     * @param options FirestoreRecyclerOptions for the ChatWindow objects.
     * @param context The context in which the adapter is running.
     */
    public RecentChatAdapter(@NonNull FirestoreRecyclerOptions<ChatWindow> options, Context context) {
        super(options);
        this.context = context;
    }

    /**
     * Binds the ChatWindow data to the ChatWindowView holder.
     *
     * @param chatWindowView The view holder for a chat window.
     * @param position The position of the chat window in the RecyclerView.
     * @param chatWindow The ChatWindow object containing chat data.
     */
    @Override
    protected void onBindViewHolder(@NonNull ChatWindowView chatWindowView, int position, @NonNull ChatWindow chatWindow) {
        String receiverUserName;

        // Determine the receiver's username based on the current user's name
        if(chatWindow.getUserNames().get(0).equals(UserCache.getInstance().getCurrentUserName())){
            receiverUserName = chatWindow.getUserNames().get(1);
        }else{
            receiverUserName = chatWindow.getUserNames().get(0);
        }

        // Bind data to the views
        if(receiverUserName.equals(UserCache.getInstance().getCurrentUserName())){
            chatWindowView.username.setText(receiverUserName + "  (Me)");
        } else{
            chatWindowView.username.setText(receiverUserName);
        }
        chatWindowView.lastMessage.setText(chatWindow.getLastMessage());
        chatWindowView.lastMessageTime.setText(FirebaseUtil.timestampToString(chatWindow.getTimestamp()));

        // Set a click listener to open the chat activity when a chat item is clicked
        chatWindowView.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            UserUtil.passUserNameAsIntent(intent,receiverUserName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    /**
     * Creates a new view holder for a chat window.
     *
     * @param parent The parent view group that this view will be attached to.
     * @param viewType The view type of the new view.
     * @return A new ChatWindowView that holds the view for the chat window.
     */
    @NonNull
    @Override
    public ChatWindowView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_view,parent,false);
        return new ChatWindowView(view);
    }

    /**
     * View holder for displaying a chat window in the RecyclerView.
     */
    class ChatWindowView extends RecyclerView.ViewHolder{
        TextView username;
        TextView lastMessage;
        TextView lastMessageTime;

        /**
         * Constructs a ChatWindowView with the specified item view.
         *
         * @param itemView The view representing the chat window.
         */
        public ChatWindowView(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.user_name_text);
            lastMessage = itemView.findViewById(R.id.last_message);
            lastMessageTime = itemView.findViewById(R.id.last_message_time);
        }
    }
}
