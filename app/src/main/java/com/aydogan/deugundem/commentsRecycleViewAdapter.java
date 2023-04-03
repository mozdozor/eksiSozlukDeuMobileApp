package com.aydogan.deugundem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class commentsRecycleViewAdapter extends RecyclerView.Adapter<commentsRecycleViewAdapter.MyViewHolder> {



    Context context;
    ArrayList<Comment> comments;

    public commentsRecycleViewAdapter(Context context, ArrayList<Comment> comments){
        this.context=context;
        this.comments=comments;

    }

    @NonNull
    @Override
    public commentsRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comment_item,parent,false);


        return new commentsRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull commentsRecycleViewAdapter.MyViewHolder holder, int position) {

        holder.commentText.setText(comments.get(position).getText());
        holder.usernameText.setText(comments.get(position).getCommentUserName());
        holder.dateText.setText(comments.get(position).getCreatedDate());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView commentText,usernameText,dateText;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText=itemView.findViewById(R.id.textCommentArea);
            usernameText=itemView.findViewById(R.id.usernameTextArea);
            dateText=itemView.findViewById(R.id.dateTextArea);
            cardView=itemView.findViewById(R.id.cardViewComment);
//
        }
    }



}
