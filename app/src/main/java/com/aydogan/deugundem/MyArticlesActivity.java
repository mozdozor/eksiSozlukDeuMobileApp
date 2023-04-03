package com.aydogan.deugundem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MyArticlesActivity extends AppCompatActivity implements SelectListener {

    ArrayList<Article> myArticles = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFireStore;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_articles);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();     //initialiaze the firebase
        mFireStore=FirebaseFirestore.getInstance();


        RecyclerView recyclerView = findViewById(R.id.recyclerViewMyArticles);

        articlesRecycleViewAdapter adapterw = new articlesRecycleViewAdapter(this,myArticles,this);
        setUpArticleModels(adapterw);
        recyclerView.setAdapter(adapterw);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }




    public void setUpArticleModels(articlesRecycleViewAdapter adapterw ){

        mUser=mAuth.getCurrentUser();

        mFireStore.collection("Articles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getData().get("userId").toString().equals(mUser.getUid().toString())){
                                    String text= document.getData().get("text").toString();
                                    String userId= document.getData().get("userId").toString();
                                    String createdDate= document.getData().get("createdDate").toString();
                                    String updatedDate= document.getData().get("updatedDate").toString();
                                    String documentId = document.getId().toString();
                                    Article articleNew = new Article(text,userId,createdDate,updatedDate,documentId);
                                    myArticles.add(articleNew);
                                }

                            }
                            adapterw.notifyDataSetChanged();

                            if(myArticles.isEmpty()){
                                Toast.makeText(MyArticlesActivity.this,"No data found", Toast.LENGTH_LONG).show();

                            }
//                            getArticleCommentCount();
                        } else {
                            System.out.println("data çekilemedi");
                        }
                    }
                });

    }










    @Override
    public void onItemClicked(Article myArticle) {

        Intent i = new Intent(MyArticlesActivity.this, ShowDetailActivity.class);
        i.putExtra("articleId",myArticle.getId());
        i.putExtra("articleName",myArticle.getText());
        startActivity(i);

    }


}