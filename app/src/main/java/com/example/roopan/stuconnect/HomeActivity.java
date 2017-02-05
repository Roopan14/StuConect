package com.example.roopan.stuconnect;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeActivity extends AppCompatActivity {

    TextView head,pick;
    Button student,authority;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        PrintKeyHash();
        firebaseAuth = FirebaseAuth.getInstance();
        try{
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            Intent intent = new Intent(HomeActivity.this,AuthNewsfeed.class);
            startActivity(intent);
            finish();
        }
        }
        catch (Exception e)
        {

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.hometool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        sharedPreferences = getSharedPreferences(Constants.REG_SHARED_PREF,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        head = (TextView) findViewById(R.id.headtextView);
        pick = (TextView) findViewById(R.id.picktextView);

        student = (Button) findViewById(R.id.studentButton);
        authority = (Button) findViewById(R.id.authButton);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("type","Student");
                editor.commit();
                Intent intent = new Intent(HomeActivity.this,StudentHome.class);
                startActivity(intent);
                finish();
            }
        });

        authority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("type","Authority");
                editor.commit();
                Intent intent = new Intent(HomeActivity.this,AuthHome.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void PrintKeyHash(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.roopan.stuconnect", PackageManager.GET_SIGNATURES);
            for (Signature signature: info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("abc : ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }catch (PackageManager.NameNotFoundException e){

        }catch (NoSuchAlgorithmException e){

        }
    }
}
