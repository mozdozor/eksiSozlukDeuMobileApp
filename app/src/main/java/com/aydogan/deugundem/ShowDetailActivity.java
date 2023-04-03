package com.aydogan.deugundem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowDetailActivity extends AppCompatActivity{

    private TextView articleNameText;
    private String articleName,comingArticleId;
    ArrayList<Comment> comments = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFireStore;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        articleNameText=findViewById(R.id.textView9);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            comingArticleId = extras.getString("articleId");
             articleName = extras.getString("articleName");
            System.out.println("bu tarafa gelen id: "+comingArticleId);
            articleNameText.setText(articleName);
        }

        mAuth = FirebaseAuth.getInstance();     //initialiaze the firebase
        mFireStore=FirebaseFirestore.getInstance();



        RecyclerView recyclerView = findViewById(R.id.recyclerViewForCommentList);

        commentsRecycleViewAdapter adapterw = new commentsRecycleViewAdapter(this,comments);
        setUpCommentModels(adapterw);
        recyclerView.setAdapter(adapterw);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }


    public void makeCommentToThisArticle(View view){

        Intent intent = new Intent(ShowDetailActivity.this, WriteCommentActivity.class);
        intent.putExtra("articleId",comingArticleId);
        intent.putExtra("articleName",articleName);
        startActivity(intent);
    }


    public void setUpCommentModels(commentsRecycleViewAdapter adapterw ){

//
//            for (int i=0;i<5;i++){
//                String text= "text metnidir buraya gelcek";
//                String userId= "qzYDYsBlmDWETqIZtCScYG7wt3K2";
//                String createdDate= "24/12/2022";
//                String updatedDate= "24/12/2022";
//
//                Article articleNew = new Article(text,userId,createdDate,updatedDate);
//                articles.add(articleNew);
//            }


//        Query query = mFireStore.collection("Comments")
//                .whereEqualTo("articleId",articleId)
//                .orderBy("createdDate", Query.Direction.DESCENDING);

        mFireStore.collection("Comments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String articleId= document.getData().get("articleId").toString();
                                String userId= document.getData().get("userId").toString();
                                String comment= document.getData().get("comment").toString();
                                String createdDate= document.getData().get("createdDate").toString();
                                String updatedDate= document.getData().get("updatedDate").toString();
                                String documentId = document.getId().toString();
                                String nameAndSurname = document.getData().get("nameAndSurname").toString();
                                if(articleId.equals(comingArticleId)){
                                    Comment commentNew = new Comment(documentId,userId,articleId,nameAndSurname,comment,createdDate,updatedDate);
                                    comments.add(commentNew);
                                }

//                                System.out.println("documentId : "+document.getId());
//                                System.out.println("documentData : "+document.getData().get("text"));
                            }
                            adapterw.notifyDataSetChanged();
                        } else {
                            System.out.println("data çekilemedi");
                        }
                    }
                });

    }







}