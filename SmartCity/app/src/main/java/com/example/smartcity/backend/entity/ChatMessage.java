package com.example.smartcity.backend.entity;

/**
 * ChatMessage.java
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

/**
 * Represents a chat message in the chat application.
 * This class holds the message content, the user who sent it, and the timestamp of when it was sent.
 */
public class ChatMessage {
    private String message;
    private String userName;
    private Timestamp timestamp;

    public ChatMessage(){}

    /**
     * Constructs a new ChatMessage with the specified message content, user name, and timestamp.
     *
     * @param message   The content of the chat message.
     * @param userName  The name of the user who sent the message.
     * @param timestamp The time when the message was sent.
     */
    public ChatMessage(String message, String userName, Timestamp timestamp) {
        this.message = message;
        this.userName = userName;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
