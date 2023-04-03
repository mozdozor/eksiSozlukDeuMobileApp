package com.aydogan.deugundem;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    ArrayList<Article> articles = new ArrayList<>();
//    private FirebaseAuth mAuth;
//    private FirebaseUser mUser;
//    private FirebaseFirestore mFireStore;
//    private DocumentReference docRef;




    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate funcdo");
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("onCreateView funcdo");
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        // Inflate the layout for this fragment
//        mAuth = FirebaseAuth.getInstance();     //initialiaze the firebase
//        mFireStore=FirebaseFirestore.getInstance();
//        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//        setUpArticleModels();
//
//
//        articlesRecycleViewAdapter adapterw = new articlesRecycleViewAdapter(getContext(),articles);
//        recyclerView.setAdapter(adapterw);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button openAllArticlesPage = (Button) view.findViewById(R.id.button10);
        openAllArticlesPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), AllArticlesActivity.class);
                getActivity().startActivity(intent);
            }
        });


        Button openMyArticlesPage = (Button) view.findViewById(R.id.button12);
        openMyArticlesPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), MyArticlesActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }


//
//    public void setUpArticleModels(){
//
//        mFireStore.collection("Articles")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                String text= document.getData().get("text").toString();
//                                String userId= document.getData().get("userId").toString();
//                                String createdDate= document.getData().get("createdDate").toString();
//                                String updatedDate= document.getData().get("updatedDate").toString();
//
//                                Article articleNew = new Article(text,userId,createdDate,updatedDate);
//                                articles.add(articleNew);
////                                System.out.println("documentId : "+document.getId());
////                                System.out.println("documentData : "+document.getData().get("text"));
//                            }
//                        } else {
//                            System.out.println("data çekilemedi");
//                        }
//                    }
//                });
//
//    }




}