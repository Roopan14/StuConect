package com.example.roopan.stuconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roopan on 11-12-2016.
 */

public class Newsfeed extends Fragment {
    private ListView postsListView;
    List<PostPOJO> listPost;

    private PostListt postListt;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    FloatingActionButton postFAB;

    public Newsfeed(){
        // empty constructor needed
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().startService(new Intent(getContext(),LocService.class));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://stuconnect-152516.firebaseio.com/Posts");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.newsfeed,container,false);

        listPost = new ArrayList<PostPOJO>();
        postListt = new PostListt(getActivity(),listPost);
        postsListView = (ListView) fragmentView.findViewById(R.id.postList);
        //listArrayAdapter = new ArrayAdapter(getContext(),R.layout.post,listPost);
        postsListView.setAdapter(postListt);


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                PostPOJO postPOJO = dataSnapshot.getValue(PostPOJO.class);
                listPost.add(0,postPOJO);
                postListt.notifyDataSetChanged();
                /*for (DataSnapshot posts : dataSnapshot.getChildren())
                {
                    String key = posts.getKey();
                    Toast.makeText(getContext(),key,Toast.LENGTH_SHORT).show();
                    *//*PostPOJO post = dataSnapshot.child(key).getValue(PostPOJO.class);
                    listPost.add(post);
                    postListt.notifyDataSetChanged();*//*
                }
*/            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        postFAB = (FloatingActionButton) fragmentView.findViewById(R.id.postFAB);
        postFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthNewsfeed.bottomNavigationView.setVisibility(View.GONE);
                getFragmentManager().beginTransaction().replace(R.id.frame_content,Post.newInstance(),"post_fragment").addToBackStack(null).commit();
            }
        });
        return fragmentView;
    }

    public static Newsfeed newInstance() {
        
        Bundle args = new Bundle();
        
        Newsfeed fragment = new Newsfeed();
        fragment.setArguments(args);
        return fragment;
    }
}
