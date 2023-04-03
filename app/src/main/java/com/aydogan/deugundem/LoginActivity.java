package com.aydogan.deugundem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText emailLoginView,passwordLoginView;
    private String strEmail,strPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFireStore;
    private DocumentReference docRef;
    private HashMap<String,Object> userData;
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailLoginView=findViewById(R.id.editTextTextEmailAddress);
        passwordLoginView=findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();     //initialiaze the firebase
        mFireStore=FirebaseFirestore.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        passwordLoginView.setTransformationMethod(new PasswordTransformationMethod());
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);



    }


    public void loginRequest(View view){
        spinner.setVisibility(View.VISIBLE);
        strEmail=emailLoginView.getText().toString();
        strPassword=passwordLoginView.getText().toString();

        if(!TextUtils.isEmpty(strEmail) && !TextUtils.isEmpty(strPassword)){

            mAuth.signInWithEmailAndPassword(strEmail,strPassword)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser=mAuth.getCurrentUser();
                            assert mUser != null;
                            getUserData(mUser.getUid());
                            Toast.makeText(LoginActivity.this,"Success login.You are redirecting the home page..",Toast.LENGTH_SHORT).show();
                            goHomePage();

                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            spinner.setVisibility(View.GONE);
                        }
                    });


        }else{
            Toast.makeText(this,"Please fill the all input area",Toast.LENGTH_SHORT).show();

        }

    }



    private void getUserData(String uid){

        docRef=mFireStore.collection("Users").document(uid);
         docRef.get()
                 .addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                     @Override
                     public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                userData= (HashMap<String, Object>) documentSnapshot.getData();

                                for (Map.Entry data : userData.entrySet())
                                    System.out.println(data.getKey()+"="+data.getValue());
                            }
                     }
                 }).addOnFailureListener(this, new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                     }
                 });


    }


    public void goRegisterPage(View view){

        Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);

    }


    public void goHomePage(){

        Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
        startActivity(i);

    }



}