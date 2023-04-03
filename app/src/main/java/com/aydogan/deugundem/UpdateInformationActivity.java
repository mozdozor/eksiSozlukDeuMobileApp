package com.aydogan.deugundem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UpdateInformationActivity extends AppCompatActivity {

    EditText emailEdit,firstNameEdit,lastNameEdit;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFireStore;
    private HashMap<String,Object> userData;
    private DocumentReference docRef;
    public String userFirstName="",userLastName="",firstNameStr,lastNameStr;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);
        mAuth = FirebaseAuth.getInstance();
        mFireStore=FirebaseFirestore.getInstance();
        mUser=mAuth.getCurrentUser();
        System.out.println("user first name:"+userFirstName);
        System.out.println("user last name:"+userLastName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firstNameEdit=findViewById(R.id.editTextTextPersonName2);
        lastNameEdit=findViewById(R.id.editTextTextPersonName5);
        getUserName(mUser.getUid());
        spinner=(ProgressBar)findViewById(R.id.progressBarUpdateInformations);
        spinner.setVisibility(View.GONE);


    }



    public void updateInformations(View view){

//        emailStr = emailEdit.getText().toString();
        firstNameStr = firstNameEdit.getText().toString();
        lastNameStr = lastNameEdit.getText().toString();
        userData = new HashMap<>();

        if(!TextUtils.isEmpty(firstNameStr) && !TextUtils.isEmpty(lastNameStr) ){
            mUser = mAuth.getCurrentUser();
//            getUserEmailAndPassword(mUser.getUid().toString());
            // Get auth credentials from the user for re-authentication
//            userData.put("userEmail", emailStr);
            userData.put("userFirstName", firstNameStr);
            userData.put("userLastName", lastNameStr);

            updateDatabase(userData,mUser.getUid());


        }else
            Toast.makeText(UpdateInformationActivity.this,"Please fill the all input", Toast.LENGTH_SHORT).show();

    }


    public void getUserName(String uid) {
        System.out.println("getusername:"+uid);
        docRef = mFireStore.collection("Users").document(uid);
        docRef.get()
                .addOnSuccessListener( this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            HashMap<String, Object> userData = (HashMap<String, Object>) documentSnapshot.getData();
                            userFirstName = userData.get("userFirstName").toString();
                            userLastName = userData.get("userLastName").toString();
                            firstNameEdit.setText(userFirstName);
                            lastNameEdit.setText(userLastName);
                            System.out.println("returned user name:"+userFirstName);
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateInformationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }



    public void updateDatabase(HashMap<String, Object> hashMap,final String uuiOfUser){
        spinner.setVisibility(View.VISIBLE);
        mFireStore.collection("Users").document(uuiOfUser)
                .update(hashMap)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateInformationActivity.this, "Updated informations succesfully", Toast.LENGTH_SHORT).show();

//                            emailEdit.setText("");
//                            firstNameEdit.setText("");
//                            lastNameEdit.setText("");
                        }else{
                            Toast.makeText(UpdateInformationActivity.this, "Error! not updated", Toast.LENGTH_SHORT).show();
                        }
                        spinner.setVisibility(View.GONE);
                    }
                });
    }


}