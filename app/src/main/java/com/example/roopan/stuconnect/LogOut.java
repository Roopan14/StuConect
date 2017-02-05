package com.example.roopan.stuconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Roopan on 19-12-2016.
 */

public class LogOut extends Fragment{

    private Button logout;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener authStateListener;
    public LogOut()
    {
        //empty reqd
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.logout,container,false);
        logout = (Button) fragmentView.findViewById(R.id.signout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
                LoginManager.getInstance().logOut();
                getFragmentManager().getFragments().clear();
                Intent intent = new Intent(getActivity(),HomeActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        return fragmentView;
    }

    public static LogOut newInstance() {

        Bundle args = new Bundle();

        LogOut fragment = new LogOut();
        fragment.setArguments(args);
        return fragment;
    }
}
