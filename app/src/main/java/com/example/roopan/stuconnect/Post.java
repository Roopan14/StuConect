package com.example.roopan.stuconnect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by Roopan on 05-01-2017.
 */

public class Post extends Fragment {
    
    Button post;
    EditText postEdit;
    String userPost,name,time;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post,container,false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        post = (Button) view.findViewById(R.id.postButton);
        postEdit = (EditText) view.findViewById(R.id.postEdit);


        
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(postEdit.getText().toString()))
                {
                    userPost = postEdit.getText().toString();
                    name = firebaseAuth.getCurrentUser().getDisplayName();
                    time = new Date().toString();
                    postEdit.setText("");
                    PostPOJO postPOJO = new PostPOJO(userPost,name,time);
                    databaseReference.child("Posts").push().setValue(postPOJO);
                    //sendtoServer(userPost,name,time);
                }
                getFragmentManager().popBackStack();
                AuthNewsfeed.bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private void sendtoServer(String userPost, String name, String time) {

        PostPOJO postPOJO = new PostPOJO(userPost,name,time);
        databaseReference.child("Posts").push().setValue(postPOJO);
    }


    public static Post newInstance() {
        
        Bundle args = new Bundle();
        
        Post fragment = new Post();
        fragment.setArguments(args);
        return fragment;
    }
}
