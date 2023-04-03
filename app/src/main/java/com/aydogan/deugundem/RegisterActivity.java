package com.aydogan.deugundem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    private TextView emailTextView,nameTextView,surnameTextView,password1TextView,password2TextView;
    private String strEmail,strPassword1,strPassword2,strName,strSurname;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFireStore;
    private HashMap<String,Object> userData;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        emailTextView=findViewById(R.id.editTextTextEmailAddress2);
        nameTextView=findViewById(R.id.editTextTextPersonName3);
        surnameTextView=findViewById(R.id.editTextTextPersonName4);
        password1TextView=findViewById(R.id.editTextTextPassword2);
        password2TextView=findViewById(R.id.editTextTextPassword3);
        mFireStore=FirebaseFirestore.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner=(ProgressBar)findViewById(R.id.progressBarRegister);
        spinner.setVisibility(View.GONE);
    }


    public void registerRequest(View view){
        spinner.setVisibility(View.VISIBLE);
        strEmail= emailTextView.getText().toString();
        strName= nameTextView.getText().toString();
        strSurname= surnameTextView.getText().toString();
        strPassword1= password1TextView.getText().toString();
        strPassword2= password2TextView.getText().toString();

        if(!TextUtils.isEmpty(strEmail) && !TextUtils.isEmpty(strName) && !TextUtils.isEmpty(strSurname) && !TextUtils.isEmpty(strPassword1) && !TextUtils.isEmpty(strPassword2)){
            if(strPassword1.equals(strPassword2)){
                mAuth.createUserWithEmailAndPassword(strEmail,strPassword1)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mUser=mAuth.getCurrentUser();

                                    //FireStore
                                    userData=new HashMap<>();
                                    userData.put("userEmail",strEmail);
                                    userData.put("userFirstName",strName);
                                    userData.put("userLastName",strSurname);
                                    userData.put("userPassword",strPassword1);
                                    userData.put("userId",mUser.getUid());

                                    mFireStore.collection("Users").document(mUser.getUid())
                                                    .set(userData)
                                                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        Toast.makeText(RegisterActivity.this,"Registiration successful.You are redirecting home page ",Toast.LENGTH_SHORT).show();
                                                                        goHomePage();
                                                                        spinner.setVisibility(View.GONE);
                                                                    }
                                                                    else{
                                                                        Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });


                                }


                                else{
                                    Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    spinner.setVisibility(View.GONE);
                                }
                            }
                        });
            }else{
                Toast.makeText(this,"Passwords did not match",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Please fill the all input area",Toast.LENGTH_SHORT).show();
        }


    }


    public void goLoginPage(View view){

        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(i);

    }

    public void goHomePage(){

        Intent i = new Intent(RegisterActivity.this,HomePageActivity.class);
        startActivity(i);

    }



}