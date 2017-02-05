package com.example.roopan.stuconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class ChatActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ListView chatlayout;
    Button send;
    EditText chatedit;
    RelativeLayout relativeLayout;
    TextView messagetextview;
    MessageAdapter messageAdapter;
    String name,message;
    String uniqueName;
    ArrayList<MessagePOJO> messageList;
    private FirebaseListAdapter<MessagePOJO> adapter;
    private ArrayAdapter<MessagePOJO> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        uniqueName = intent.getStringExtra("uniqueName");
        messageList = new ArrayList<MessagePOJO>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://stuconnect-152516.firebaseio.com/Messages/"+uniqueName);

        chatlayout = (ListView) findViewById(R.id.chatlinear);
        send = (Button) findViewById(R.id.chatsend);
        chatedit = (EditText) findViewById(R.id.chatedittext);


        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        //View v = layoutInflater.inflate(R.layout.chateach,chatlayout);

        //relativeLayout = (RelativeLayout) v.findViewById(R.id.chateach);
        //messagetextview = (TextView) v.findViewById(R.id.chattext);

        //messageAdapter = new MessageAdapter(this,messageList);
        //chatlayout.setAdapter(messageAdapter);


        adapter = new FirebaseListAdapter<MessagePOJO>(ChatActivity.this, MessagePOJO.class, R.layout.chateach, databaseReference ) {
            @Override
            protected void populateView(View v, MessagePOJO model, int position) {

                MessagePOJO pojo = (MessagePOJO) model;

                TextView messagetext = (TextView) v.findViewById(R.id.chattext);
                messagetext.setText(pojo.getMessage());

                if (pojo.getName() != FirebaseAuth.getInstance().getCurrentUser().getDisplayName())
                {
                    messagetext.setGravity(Gravity.LEFT);
                }
            }
        };

        chatlayout.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        /*try {
            getChat();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(chatedit.getText().toString())){

                    name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    message = chatedit.getText().toString();

                    MessagePOJO messagePOJO = new MessagePOJO(name,message);
                    //messageList.add(messagePOJO);
                    //messageAdapter.addAll(messageList);

                    databaseReference.push().setValue(messagePOJO);
                    try {
                        //getChat();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    chatedit.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }

    private void getChat() throws Exception{

        databaseReference = firebaseDatabase.getReferenceFromUrl("https://stuconnect-152516.firebaseio.com/Messages/"+uniqueName);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    MessagePOJO messagePOJO = dataSnapshot1.getValue(MessagePOJO.class);
                    name = messagePOJO.getName();
                    message = messagePOJO.getMessage();
                    Log.d("message", message);

                    //messagetextview.setText(message);
                    //relativeLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                    messageList.add(messagePOJO);


                    if (name != FirebaseAuth.getInstance().getCurrentUser().getDisplayName()) {

                        messagetextview.setGravity(Gravity.LEFT);
                        relativeLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        //relativeLayout.setGravity(RelativeLayout.ALIGN_PARENT_RIGHT);

                    }


                }
                messageAdapter.addAll(messageList);
                }

                @Override
                public void onCancelled (DatabaseError databaseError){

                }
        });

    }
}
