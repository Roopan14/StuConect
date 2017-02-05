package com.example.roopan.stuconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    TextInputLayout emailLayout;
    TextInputEditText mName,mEmail,mMobile,mPass,mConfirmpass;

    AppCompatButton regButton;

    String email,pass;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.regtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.reg_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        emailLayout = (TextInputLayout) findViewById(R.id.emailLayout);
        regButton = (AppCompatButton) findViewById(R.id.regButton);

        mName = (TextInputEditText) findViewById(R.id.nameText);
        mEmail = (TextInputEditText) findViewById(R.id.emailText);
        mMobile = (TextInputEditText) findViewById(R.id.mobText);
        mPass = (TextInputEditText) findViewById(R.id.passEdit);
        mConfirmpass = (TextInputEditText) findViewById(R.id.passConfirm);

        sharedPreferences = getSharedPreferences(Constants.REG_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mEmail.getText().toString();
                pass = mPass.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getBaseContext(),"Registration Success",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                editor.putString("email",email);
                editor.putString("pass",pass);
                editor.commit();
                Intent intent = new Intent();
                setResult(101,intent);
                finish();
            }
        });


    }
}
