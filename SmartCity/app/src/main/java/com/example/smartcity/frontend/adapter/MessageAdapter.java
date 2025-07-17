package com.example.smartcity.frontend.adapter;

/**
 * MessageAdapter.java
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.backend.cache.UserCache;
import com.example.smartcity.backend.entity.ChatMessage;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

/**
 * Adapter for displaying chat messages in a RecyclerView.
 * This adapter binds chat message data to views for each message item,
 * ensuring that messages sent by the current user are displayed differently
 * from those sent by others.
 */
public class MessageAdapter extends FirestoreRecyclerAdapter<ChatMessage, MessageAdapter.ChatView> {

    Context context;

    /**
     * Constructs a MessageAdapter with the specified options and context.
     *
     * @param options FirestoreRecyclerOptions for the ChatMessage objects.
     * @param context The context in which the adapter is running.
     */
    public MessageAdapter(@NonNull FirestoreRecyclerOptions<ChatMessage> options, Context context) {
        super(options);
        this.context = context;
    }

    /**
     * Binds the ChatMessage data to the ChatView holder.
     *
     * @param chatView The view holder for a chat message.
     * @param position The position of the message in the RecyclerView.
     * @param message The ChatMessage object containing message data.
     */
    @Override
    protected void onBindViewHolder(@NonNull ChatView chatView, int position, @NonNull ChatMessage message) {
        // Check if the message is sent by the current user
        if(message.getUserName().equals(UserCache.getInstance().getCurrentUserName())){
            chatView.leftChatLayout.setVisibility(View.GONE);
            chatView.rightChatLayout.setVisibility(View.VISIBLE);
            chatView.rightChatTextview.setText(message.getMessage());
        }else{
            chatView.rightChatLayout.setVisibility(View.GONE);
            chatView.leftChatLayout.setVisibility(View.VISIBLE);
            chatView.leftChatTextview.setText(message.getMessage());
        }
    }

    /**
     * Creates a new view holder for a chat message.
     *
     * @param parent The parent view group that this view will be attached to.
     * @param viewType The view type of the new view.
     * @return A new ChatView that holds the view for the message.
     */
    @NonNull
    @Override
    public ChatView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_view,parent,false);
        return new ChatView(view);
    }

    /**
     * View holder for displaying a chat message in the RecyclerView.
     */
    class ChatView extends RecyclerView.ViewHolder{
        LinearLayout leftChatLayout,rightChatLayout;
        TextView leftChatTextview,rightChatTextview;

        /**
         * Constructs a ChatView with the specified item view.
         *
         * @param itemView The view representing the chat message.
         */
        public ChatView(@NonNull View itemView) {
            super(itemView);
            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatTextview = itemView.findViewById(R.id.left_chat_textview);
            rightChatTextview = itemView.findViewById(R.id.right_chat_textview);
        }
    }
}