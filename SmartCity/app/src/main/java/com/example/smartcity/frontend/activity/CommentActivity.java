package com.example.smartcity.frontend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcity.R;
import com.example.smartcity.frontend.adapter.CommentAdapter;
import com.example.smartcity.backend.entity.Comment;
import com.example.smartcity.backend.factory.CommentFactory;
import com.example.smartcity.backend.factory.ContentInComment;
import com.example.smartcity.backend.entity.Restaurant;
import com.example.smartcity.backend.factory.UsernameInComment;

import java.util.ArrayList;
import java.util.List;

/**
 * CommentActivity is new page of comment
 * it shows the restaurant which is clicked and the comment of it
 * the comment will display automatically
 * and the username change to "Me" when user submit the his comment
 */
public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView restaurantPhoto;
    private TextView textViewName, textViewRating, textViewAddress;
    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private EditText editTextComment;
    private Button buttonSubmitComment;
    private String currentUsername = "Me";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_comment);

        restaurantPhoto = findViewById(R.id.restaurant_photo);
        textViewName = findViewById(R.id.textView_name);
        textViewRating = findViewById(R.id.textView_rating);
        textViewAddress = findViewById(R.id.textView_address);
        recyclerViewComments = findViewById(R.id.recyclerView_comments);
        editTextComment = findViewById(R.id.editText_comment);
        buttonSubmitComment = findViewById(R.id.button_submit_comment);

        // comment list
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList, currentUsername);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComments.setAdapter(commentAdapter);

        // display the res
        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
        if (restaurant != null) {
            String photoUrl = restaurant.getPhoto_url();
            if (photoUrl != null && !photoUrl.isEmpty()) {
                Glide.with(this).load(photoUrl).into(restaurantPhoto);
            } else {
                restaurantPhoto.setImageResource(R.drawable.ic_avatar);
            }
            textViewName.setText(restaurant.getName());
            textViewRating.setText(String.valueOf(restaurant.getRating()));
            textViewAddress.setText(restaurant.getAddress());
        }

        // listen submit the comment
        buttonSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = editTextComment.getText().toString();
                if (!commentText.isEmpty()) {
                    Comment comment = new Comment(currentUsername, commentText);
                    commentList.add(comment);
                    commentAdapter.notifyItemInserted(commentList.size() - 1);
                    editTextComment.setText("");
                }
            }
        });

        // simulate the comments
        recyclerViewComments.postDelayed(new Runnable() {
            @Override
            public void run() {
                addRandomComment();
                recyclerViewComments.postDelayed(this, 3000);
            }
        }, 3000);
        //return to item page
        //this img from https://www.iconfont.cn/search/index?searchType=icon&q=%E8%BF%94%E5%9B%9E
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    //simulate the data stream
    private void addRandomComment() {
        CommentFactory commentFactory = new CommentFactory();
        ContentInComment contentInComment = (ContentInComment) commentFactory.getItem("Comment");
        UsernameInComment usernameInComment = (UsernameInComment) commentFactory.getItem("Username");
        Comment randomComment = new Comment(usernameInComment.getItem(), contentInComment.getItem());
        commentList.add(randomComment);
        commentAdapter.notifyItemInserted(commentList.size() - 1);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back){
            finish();
        }
    }
}
