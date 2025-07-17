package com.example.smartcity.backend.entity;

/**
 * ChatWindow.java
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

import java.util.List;

/**
 * Represents a chat window in the chat application.
 * This class holds the details of a chat window, including the ID,
 * participants, last message, and timestamp of the last activity.
 */
public class ChatWindow {
    String chatWindowId;
    List<String> userNames;
    Timestamp timestamp;
    String lastMessageUserName;
    String lastMessage;


    public ChatWindow(){}

    /**
     * Constructs a new ChatWindow with the specified parameters.
     *
     * @param chatWindowId        The unique identifier for the chat window.
     * @param userNames           The list of user names participating in the chat.
     * @param timestamp           The time when the last message was sent in the chat.
     * @param lastMessageUserName The name of the user who sent the last message.
     */
    public ChatWindow(String chatWindowId, List<String> userNames, Timestamp timestamp, String lastMessageUserName) {
        this.chatWindowId = chatWindowId;
        this.userNames = userNames;
        this.timestamp = timestamp;
        this.lastMessageUserName = lastMessageUserName;
    }

    public String getChatWindowId() {
        return chatWindowId;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getLastMessageUserName() {
        return lastMessageUserName;
    }

    public String getLastMessage() {
        return lastMessage;
    }
    public void setChatWindowId(String chatWindowId) {
        this.chatWindowId = chatWindowId;
    }

    public void setUserNames(List<String> userIds) {
        this.userNames = userIds;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setLastMessageUserName(String lastMessageUserName) {
        this.lastMessageUserName = lastMessageUserName;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
