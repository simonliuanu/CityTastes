package com.example.smartcity.frontend.activity;

/**
 * ChatActivity.java
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.frontend.adapter.MessageAdapter;
import com.example.smartcity.backend.cache.UserCache;
import com.example.smartcity.backend.entity.ChatMessage;
import com.example.smartcity.backend.entity.ChatWindow;
import com.example.smartcity.backend.entity.User;
import com.example.smartcity.util.FirebaseUtil;
import com.example.smartcity.util.UserUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

/**
 * This activity handles the chat interface where users can send and receive messages
 * to and from each other. It initializes the chat window and displays chat messages
 * in real-time using Firestore.
 */
public class ChatActivity extends AppCompatActivity {

    User receiverUser;
    String chatWindowId;
    ChatWindow chatWindow;
    MessageAdapter adapter;


    EditText messageInput;
    ImageButton sendBt;
    ImageButton backBt;
    TextView receiverUserName;
    RecyclerView recyclerView;

    /**
     * Called when the activity is created. Initializes the UI components,
     * retrieves user information from the intent, and sets up the chat window.
     *
     * @param savedInstanceState A Bundle containing the saved instance state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Retrieve user information from the intent
        receiverUser = UserUtil.getUserFromIntent(getIntent());
        chatWindowId = FirebaseUtil.getChatWindowId(UserCache.getInstance().getCurrentUserName(), receiverUser.getName());

        messageInput = findViewById(R.id.message_input);
        sendBt = findViewById(R.id.send_btn);
        backBt = findViewById(R.id.back_btn);
        receiverUserName = findViewById(R.id.receiver_user_name);
        recyclerView = findViewById(R.id.chat_view);

        // Back button functionality to return to ChatFragment
        backBt.setOnClickListener((v -> {
            Intent intent = new Intent(ChatActivity.this, MainActivity.class);
            intent.putExtra("targetFragment", "ChatFragment");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }));

        // Display the receiver's username
        receiverUserName.setText(receiverUser.getName());

        // Fetch or create the chat window
        getChatWindow();

        // Send button functionality to send a message
        sendBt.setOnClickListener((v -> {
            String message = messageInput.getText().toString().trim();
            if(message.isEmpty())
                return;
            seedMessage(message);
        }));

        // Set up the message display
        getMessageView();
    }

    /**
     * Retrieve or create the chat window for the current conversation.
     * If the chat window does not exist, a new one is created and saved
     * in the Firestore database.
     */
    void getChatWindow(){
        FirebaseUtil.getChatWindows(chatWindowId).get().addOnCompleteListener(task ->{
            if(task.isSuccessful()){
                chatWindow = task.getResult().toObject(ChatWindow.class);
                if(chatWindow == null){
                    // Create a new chat window if it does not exist
                    chatWindow = new ChatWindow(
                            chatWindowId,
                            Arrays.asList(UserCache.getInstance().getCurrentUserName(), receiverUser.getName()),
                            Timestamp.now(),
                            ""
                    );
                    FirebaseUtil.getChatWindows(chatWindowId).set(chatWindow);
                }
            }
        });
    }

    /**
     * Send a message and update chat window details.
     * Updates the timestamp, last message, and last message sender in the chat window,
     * and adds the new message to the chat messages collection.
     *
     * @param message The message to be sent.
     */
    void seedMessage(String message){

        chatWindow.setTimestamp(Timestamp.now());
        chatWindow.setLastMessageUserName(UserCache.getInstance().getCurrentUserName());
        chatWindow.setLastMessage(message);
        FirebaseUtil.getChatWindows(chatWindowId).set(chatWindow);

        // Create and send the message
        ChatMessage chatMessage = new ChatMessage(message, UserCache.getInstance().getCurrentUserName(),Timestamp.now());
        FirebaseUtil.getChatMessages(chatWindowId).add(chatMessage).addOnCompleteListener(new OnCompleteListener<DocumentReference>(){
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    messageInput.setText("");
                }
            }
        });
    }

    /**
     * Set up the message display in RecyclerView.
     * Queries the chat messages for the current conversation and displays them
     * in a RecyclerView with the latest messages at the bottom.
     */
    void getMessageView(){
        Query query = FirebaseUtil.getChatMessages(chatWindowId)
                .orderBy("timestamp", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<ChatMessage> options = new FirestoreRecyclerOptions.Builder<ChatMessage>()
                .setQuery(query,ChatMessage.class).build();

        adapter = new MessageAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);

        // Display the latest messages at the bottom
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        // Smooth scroll to the top when new messages are added
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }
}

