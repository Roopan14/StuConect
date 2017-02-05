package com.example.roopan.stuconnect;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Roopan on 11-12-2016.
 */

public class Profile extends Fragment {

    TextView profileName,profileMail,profileType;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Profile(){
        // empty constructor needed
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(Constants.REG_SHARED_PREF, Context.MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.profile,container,false);
        profileName = (TextView) fragmentView.findViewById(R.id.nameProfile);
        profileMail = (TextView) fragmentView.findViewById(R.id.emailProfile);
        profileType = (TextView) fragmentView.findViewById(R.id.typeProfile);

        profileName.setText(firebaseAuth.getCurrentUser().getDisplayName());
        profileMail.setText("Email\t:\t"+firebaseAuth.getCurrentUser().getEmail());
        profileType.setText("Account type\t:\t"+sharedPreferences.getString("type",""));


        return fragmentView;
    }

    public static Profile newInstance() {
        
        Bundle args = new Bundle();
        
        Profile fragment = new Profile();
        fragment.setArguments(args);
        return fragment;
    }
}
