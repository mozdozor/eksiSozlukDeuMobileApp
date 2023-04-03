package com.aydogan.deugundem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class articlesRecycleViewAdapter extends RecyclerView.Adapter<articlesRecycleViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Article> articles;
    private SelectListener listener;

    public articlesRecycleViewAdapter(Context context, ArrayList<Article> articles,SelectListener listener){
        this.context=context;
        this.articles=articles;
        this.listener=listener;

    }

    @NonNull
    @Override
    public articlesRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.article_item_2,parent,false);


        return new articlesRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull articlesRecycleViewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.articleNameText.setText(articles.get(position).getText());
        holder.articleCommentCountText.setText("");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(articles.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView articleNameText,articleCommentCountText;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            articleNameText=itemView.findViewById(R.id.textCommentArea);
            articleCommentCountText=itemView.findViewById(R.id.textView8);
            cardView=itemView.findViewById(R.id.cardViewComment);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    System.out.println("basılan text "+articleNameText.getText());
//                }
//            });
        }
    }

}
