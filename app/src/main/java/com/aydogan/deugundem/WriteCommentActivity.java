package com.aydogan.deugundem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class WriteCommentActivity extends AppCompatActivity {
    private TextView articleNameText,commentText;
    public String articleName,articleId,concatNameUser="";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFireStore;
    private DocumentReference docRef;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();     //initialiaze the firebase
        mFireStore=FirebaseFirestore.getInstance();
        articleNameText=findViewById(R.id.textView11);
        commentText=findViewById(R.id.makeCommentText);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            articleId = extras.getString("articleId");
            articleName = extras.getString("articleName");
            System.out.println("write id: "+articleId);
            articleNameText.setText(articleName);
        }
        mUser=mAuth.getCurrentUser();
        getUserName(mUser.getUid());
        spinner=(ProgressBar)findViewById(R.id.progressBarAddComment);
        spinner.setVisibility(View.GONE);
    }


    public void saveCommentToDatabase(View view){
        spinner.setVisibility(View.VISIBLE);
        HashMap<String, Object> commentData = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        commentData.put("comment",commentText.getText().toString());
        commentData.put("userId",mUser.getUid());
        commentData.put("articleId",articleId);
        commentData.put("nameAndSurname",concatNameUser);
        System.out.println("alınan nameAndSurname "+concatNameUser);
        commentData.put("createdDate",formatter.format(date).toString());
        commentData.put("updatedDate",formatter.format(date).toString());


        mFireStore.collection("Comments")
                .add(commentData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(WriteCommentActivity.this,"Comment added successfully ",Toast.LENGTH_SHORT).show();
                        commentText.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(WriteCommentActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        spinner.setVisibility(View.GONE);
                    }
                });
    }



    public void getUserName(String uid) {

        docRef = mFireStore.collection("Users").document(uid);
        docRef.get()
                .addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            HashMap<String, Object> userData = (HashMap<String, Object>) documentSnapshot.getData();
                            // System.out.println(userData.get("userPassword"));
                            concatNameUser = userData.get("userFirstName").toString()+" "+userData.get("userLastName").toString();
                            System.out.println("concatNameUser: "+concatNameUser);
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(WriteCommentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }






}