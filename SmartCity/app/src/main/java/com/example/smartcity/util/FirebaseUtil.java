package com.example.smartcity.util;

/**
 * FirebaseUtil.java
 * This file is part of the chat functionality implementation,
 * which is adapted from the tutorial series by Bimal Kafle.
 *
 * <p>Sources:</p>
 * <ul>
 *     <li>YouTube Playlist: <a href="https://www.youtube.com/playlist?list=PLgpnJydBcnPB-aQ6P5hWCHBjy8LWZ9x4w">YouTube Playlist</a></li>
 *     <li>GitHub Repository: <a href="https://github.com/bimalkaf/Android_Chat_Application">GitHub Repository</a></li>
 * </ul>
 */

import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * Utility class for interacting with Firebase services, providing methods
 * for accessing Firestore and Realtime Database references.
 * This utility class facilitates the implementation of chat functionalities
 * by providing methods to manage chat windows and messages.
 */
public class FirebaseUtil {

    /**
     * Returns a reference to the "restaurants" node in Firebase Realtime Database.
     *
     * @return A DatabaseReference pointing to the "restaurants" node.
     */
    public static DatabaseReference getRestaurantReference() {
        return FirebaseDatabase.getInstance().getReference().child("restaurants");
    }

    /**
     * Returns a reference to the "users" collection in Firestore.
     *
     * @return A CollectionReference pointing to the "users" collection.
     */
    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }

    /**
     * Returns a reference to the "chatWindows" collection in Firestore.
     *
     * @return A CollectionReference pointing to the "chatWindows" collection.
     */
    public static CollectionReference allChatWindowCollectionReference(){
        return FirebaseFirestore.getInstance().collection("chatWindows");
    }

    /**
     * Converts a Firestore Timestamp to a formatted string.
     *
     * @param timestamp The Timestamp to be converted.
     * @return A string representing the formatted date and time.
     */
    public static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("MM-dd HH:MM").format(timestamp.toDate());
    }

    /**
     * Returns a reference to a specific chat window document in Firestore.
     *
     * @param chatWindowId The ID of the chat window.
     * @return A DocumentReference pointing to the specified chat window.
     */
    public static DocumentReference getChatWindows(String chatWindowId){
        return FirebaseFirestore.getInstance().collection("chatWindows").document(chatWindowId);
    }

    /**
     * Generates a unique chat window ID based on two user IDs.
     * The IDs are sorted alphabetically to ensure consistency.
     *
     * @param userId1 The first user ID.
     * @param userId2 The second user ID.
     * @return A unique chat window ID formed by combining the two user IDs.
     */
    public static String getChatWindowId(String userId1, String userId2) {
        String[] users = {userId1, userId2};
        Arrays.sort(users);
        return users[0] + "_" + users[1];
    }

    /**
     * Returns a reference to the "Messages" subcollection of a specific chat window.
     *
     * @param chatWindowId The ID of the chat window.
     * @return A CollectionReference pointing to the "Messages" subcollection.
     */
    public static CollectionReference getChatMessages(String chatWindowId){
        return getChatWindows(chatWindowId).collection("Messages");
    }


}
