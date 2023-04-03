package com.aydogan.deugundem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText oldPassword, newPassword1, newPassword2;
    String oldPasswordStr, newPassword1Str, newPassword2Str, currentPassword="", currentEmail="";
    private HashMap<String, Object> mData;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFireStore;
    private DocumentReference docRef;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
        oldPassword = findViewById(R.id.oldPasswordEdit);
        newPassword1 = findViewById(R.id.editTextTextPassword7);
        newPassword2 = findViewById(R.id.editTextTextPassword8);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner=(ProgressBar)findViewById(R.id.progressBarChangePassword);
        spinner.setVisibility(View.GONE);

    }


    public void changePasswordFunc(View view) {
        oldPasswordStr = oldPassword.getText().toString();
        newPassword1Str = newPassword1.getText().toString();
        newPassword2Str = newPassword2.getText().toString();

        if (!TextUtils.isEmpty(oldPasswordStr) && !TextUtils.isEmpty(newPassword1Str) && !TextUtils.isEmpty(newPassword2Str)) {

            if (newPassword1Str.equals(newPassword2Str)) {
                if(newPassword1Str.length()>=6){
                    mData = new HashMap<>();
                    mUser = mAuth.getCurrentUser();
                    assert mUser != null;
                    getUserPassword(mUser.getUid());
                    System.out.println("cuurent "+currentPassword);
                    if (currentPassword.equals(oldPasswordStr)) {
                        System.out.println("aşağıdaaaa");
                        System.out.println(mUser);
                        mData.put("userPassword", newPassword1Str);
                        updateData(newPassword1Str,mData,mUser.getUid());
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Old password incorrect", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ChangePasswordActivity.this, "Your new password must be a minimum of 6 characters long", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(ChangePasswordActivity.this, "Your new passwords do not match with each other", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(ChangePasswordActivity.this, "Plase fill the all input area", Toast.LENGTH_SHORT).show();
        }


    }


    public void updateData(String newPass, HashMap<String, Object> hashMap,String uuiOfUser) {
        spinner.setVisibility(View.VISIBLE);

        mUser = mAuth.getCurrentUser();
//        getUserPassword(uuiOfUser);

        AuthCredential credential = EmailAuthProvider
                .getCredential(currentEmail, currentPassword);

// Prompt the user to re-provide their sign-in credentials
        mUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                            updateDatabase(hashMap,uuiOfUser);
                                        //Toast.makeText(ChangePasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ChangePasswordActivity.this, "Error password not updated", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Error auth failed", Toast.LENGTH_SHORT).show();
                        }
                        spinner.setVisibility(View.GONE);
                    }
                });






    }



    public void updateDatabase(HashMap<String, Object> hashMap,final String uuiOfUser){

        mFireStore.collection("Users").document(uuiOfUser)
                .update(hashMap)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ChangePasswordActivity.this, "Your password updated successfully", Toast.LENGTH_SHORT).show();
                            oldPassword.setText("");
                            newPassword1.setText("");
                            newPassword2.setText("");
                        }
                    }
                });
    }


    public void getUserPassword(String uid) {

        docRef = mFireStore.collection("Users").document(uid);
        docRef.get()
                .addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            HashMap<String, Object> userData = (HashMap<String, Object>) documentSnapshot.getData();
                            // System.out.println(userData.get("userPassword"));
                            currentPassword = userData.get("userPassword").toString();
                            currentEmail = userData.get("userEmail").toString();
//                            for (Map.Entry data : userData.entrySet())
//                                System.out.println(data.getKey()+"="+data.getValue());
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }














}