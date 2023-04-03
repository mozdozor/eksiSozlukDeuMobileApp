package com.aydogan.deugundem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.aydogan.deugundem.databinding.ActivityHomePageBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;


public class HomePageActivity extends AppCompatActivity {


    public static final String articleName = "name";
    public static final String articleCommentCount = "age";
    private View articleLayout;
    private FirebaseFirestore firebaseFirestoreDb;
    private RecyclerView recyclerView;
    private CollectionReference collectionReference;
//    private ArticleAdapter adapter;
    private Query query;
    ActivityHomePageBinding binding;
//    ArrayList<Article> articles = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFireStore;
    private DocumentReference docRef;
    String nameAndSurname="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("home page actity");
        super.onCreate(savedInstanceState);
        binding=ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();     //initialiaze the firebase
        mFireStore=FirebaseFirestore.getInstance();
        mUser=mAuth.getCurrentUser();
        replaceFragment(new HomeFragment());   //first time run home fragment
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.article:
                    replaceFragment(new ArticleFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });

//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        setUpArticleModels();
//
//
//        articlesRecycleViewAdapter adapterw = new articlesRecycleViewAdapter(findViewById(R.id.frameLayout2).getContext(),articles);
//        recyclerView.setAdapter(adapterw);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    public void replaceFragment(Fragment fragment){
        getUserName(mUser.getUid());
        System.out.println("gelen fragment: "+fragment);
        Bundle bundle = new Bundle();
        bundle.putString("nameAndSurname", nameAndSurname);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }


    public void getUserName(String uid) {
        docRef = mFireStore.collection("Users").document(uid);
        docRef.get()
                .addOnSuccessListener( this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            HashMap<String, Object> userData = (HashMap<String, Object>) documentSnapshot.getData();
                            // System.out.println(userData.get("userPassword"));
                           nameAndSurname = userData.get("userFirstName").toString()+" "+userData.get("userLastName").toString();
//                            System.out.println("concatNameUser: "+concatNameUser);
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomePageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


}