package com.example.smartcity.frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.backend.entity.Comment;

import java.util.List;

/**
 * CommentAdapter is used to help with the activity to display the comment message normally,
 * including display the every comment through recycleView
 * and display "Me" according to the current user
 * and display the name, content, and date in every comment.
 * Reference: part idea of code comes from: https://stackoverflow.com/questions/33702163/why-to-use-static-with-recyclerview-viewholder
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;
    private String currentUsername;

    /**
     *
     * @param commentList store all comment data to display
     * @param currentUsername is used to check the user of current comment
     */
    public CommentAdapter(List<Comment> commentList, String currentUsername) {
        this.commentList = commentList;
        this.currentUsername = currentUsername;
    }

    /**
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return CommentViewHolder
     */
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    /**
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        String usernameToDisplay = comment.getUsername().equals(currentUsername) ? "Me" : comment.getUsername();
        holder.textViewUserName.setText(usernameToDisplay);
        holder.textViewComment.setText(comment.getContent());
        holder.textViewDate.setText(comment.getDate());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    /**
     * CommentViewHolder is used to hold a single comment's view
     */
    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewComment;
        TextView textViewUserName;
        TextView textViewDate;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textView_username);
            textViewComment = itemView.findViewById(R.id.textView_comment);
            textViewDate = itemView.findViewById(R.id.textView_date);
        }
    }
}
