package com.example.smartcity.backend.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** Comment class is used to abstract the String of comment
 *  and get every elements in comment
 */
public class Comment {
    private String username;
    private String content;
    private String date;

    public Comment(String username, String content) {
        this.username = username;
        this.content = content;
        this.date = getCurrentDate();
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}
