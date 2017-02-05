package com.example.roopan.stuconnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StudentHome extends AppCompatActivity {

    private static final int GOOGLE_SIGN_IN = 100;
    private TextView stutext;
    private EditText mEmail, mPass;
    private Button sign_in, sign_up;
    private static final int REG_FROM_STUDENT = 101;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String email, pass, userid, name, type;
    private LoginButton facebookButton;
    private int fbReqCode;
    CallbackManager callbackManager;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleApiClient googleApiClient;
    private SignInButton googleSignIn;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog dialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_student_home);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.token_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        //RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_student_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.stuhometool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.stu_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();

        sharedPreferences = getSharedPreferences(Constants.REG_SHARED_PREF, Context.MODE_PRIVATE);
        type = sharedPreferences.getString("type", "");
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                dialog.show();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    databaseReference = firebaseDatabase.getReference();
                    UserPojo userPojo = new UserPojo(user.getDisplayName(),user.getEmail(), user.getUid());

                    databaseReference.child("Users").child(user.getUid()).setValue(userPojo);
                    Toast.makeText(getApplicationContext(), "Logged in as " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), AuthNewsfeed.class);
                    startActivity(intent);
                    finish();
                }
                dialog.dismiss();
            }
        };

        googleSignIn = (SignInButton) findViewById(R.id.googleLogin);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, GOOGLE_SIGN_IN);
            }
        });

        facebookButton = (LoginButton) findViewById(R.id.fblogin);
        facebookButton.setReadPermissions("public_profile", "email");
        fbReqCode = CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                dialog.show();
                handleFacebookAccessToken(loginResult.getAccessToken());
                /*GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Log.d("name",name);
                            name = object.getString("name");
                            Intent intent = new Intent(getBaseContext(),AuthNewsfeed.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                graphRequest.executeAsync();
                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();*/
                dialog.dismiss();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        stutext = (TextView) findViewById(R.id.stuhomeText);

        mEmail = (EditText) findViewById(R.id.emailText);
        mPass = (EditText) findViewById(R.id.passText);

        sign_in = (Button) findViewById(R.id.signInbutton);
        sign_up = (Button) findViewById(R.id.Registerbutton);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mEmail.getText().toString();
                pass = mPass.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(StudentHome.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getBaseContext(), AuthNewsfeed.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this, Registration.class);
                startActivityForResult(intent, REG_FROM_STUDENT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == fbReqCode) {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            } else if (requestCode == GOOGLE_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthGoogle(account);
                }

            } else {

                email = sharedPreferences.getString("email", null);
                pass = sharedPreferences.getString("pass", null);

                mEmail.setText(email);
                mPass.setText(pass);
            }
        }
    }

    private void firebaseAuthGoogle(final GoogleSignInAccount account) {
        AuthCredential authCredentialGoogle = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredentialGoogle)
                .addOnCompleteListener(StudentHome.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(StudentHome.this, "Something went wrong! Try Again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void handleFacebookAccessToken(final AccessToken token) {
        AuthCredential authCredentialFB = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(authCredentialFB)
                .addOnCompleteListener(StudentHome.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            Log.e("result", task.getException().toString());
                        }
                        catch (Exception e)
                        {
                            //
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(StudentHome.this, "Something went wrong! Try Again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}