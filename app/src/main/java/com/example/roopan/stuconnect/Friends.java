package com.example.roopan.stuconnect;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Created by Roopan on 11-12-2016.
 */

public class Friends extends Fragment {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,dbRef;
    LinearLayout friendList,eachFriendList;
    TextView friendName;
    View viewLine;
    LinkedHashSet<String> names;
    ImageButton imagebutton;
    ViewGroup.LayoutParams viewlayoutParams;
    private ListView friendListView;
    private ArrayList<String> friends,uniqueID,abc;
    private static int a=0;
    private ProgressDialog dialog;
    private ArrayAdapter arrayAdapter;
    private String uID,latitude,longitude,name,date;

    public Friends(){
        // empty constructor needed
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void firendListUI() {
        /*friendName = new TextView(view.getContext());
        friendName.setLayoutParams(viewlayoutParams);
        imagebutton = new ImageButton(view.getContext());
        imagebutton.setLayoutParams(viewlayoutParams);
        imagebutton.setImageResource(R.drawable.mapicon);
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = firebaseDatabase.getReferenceFromUrl("https://stuconnect-152516.firebaseio.com/Users/"+uId);
                //get Latitude and Longitude from this Url
            }
        });
        friendName.setText(userName);*/
        //eachFriendList.addView(friendName);
        //eachFriendList.addView(imagebutton);
        /*if (a>=0)
        {
            viewLine.setVisibility(View.VISIBLE);
            views.add(viewLine);
        }*/

        //views.add(friendName);


        arrayAdapter = new ArrayAdapter(getActivity(),R.layout.listview,friends.toArray());
        friendListView.setAdapter(arrayAdapter);
        //friendList.addView(friendName);
        //a++;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.friends,container,false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://stuconnect-152516.firebaseio.com/Users");
        friends = new ArrayList<String>();
        uniqueID = new ArrayList<String>();

        /*friendList = (LinearLayout) fragmentView.findViewById(R.id.friendListLinear);
        eachFriendList = (LinearLayout) fragmentView.findViewById(R.id.eachFriend);
        viewLine = (View) fragmentView.findViewById(R.id.lineSeperator);
        views = new ArrayList<View>();
*/
        friendListView = (ListView) fragmentView.findViewById(R.id.friendList);

        /*eachFriendList = new LinearLayout(fragmentView.getContext());
        eachFriendList.setOrientation(LinearLayout.HORIZONTAL);
        eachFriendList.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        friendList = new LinearLayout(fragmentView.getContext());
        friendList.setOrientation(LinearLayout.VERTICAL);
        friendList.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
*/
        //viewlayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        names = new LinkedHashSet<String>();
        dialog = new ProgressDialog(getContext(),ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait");
        dialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {


                new AsyncTask<Void,Void,Void>()
                {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        //dialog.show();
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        if(dataSnapshot.exists())
                        {
                            for(DataSnapshot userToken : dataSnapshot.getChildren())
                            {
                                //if (!userToken.getKey().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                    UserPojo user = userToken.getValue(UserPojo.class);
                                    friends.add(user.getUserName());
                                    uniqueID.add(user.getUid());
                                    //Toast.makeText(getContext(),userName,Toast.LENGTH_SHORT).show();
                                    //friends[i]=userName;
                                    //uniqueID[i]=uId;
                                    //i++;
                                //}
                            }
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        /*String userName = "";
                        String uId = "";
                        for(int b =0;b<i;b++)
                        {
                            userName = friends[b];
                            uId = uniqueID[b];
                        }*/
                        //friendList.addFocusables(views,View.FOCUS_DOWN,View.FOCUSABLES_TOUCH_MODE);
                        firendListUI();
                        dialog.dismiss();
                    }
                }.execute();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                uID = uniqueID.get(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setPositiveButton("Message", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbRef = firebaseDatabase.getReferenceFromUrl("https://stuconnect-152516.firebaseio.com/Users/"+uID);
                        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                name = dataSnapshot.child("userName").getValue(String.class);
                                Log.d("friend_name", name);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        String myname = "myname";
                        String frndname = "frndname";

                        myname = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                        frndname = name;
                        names.add(myname);
                        names.add(frndname);

                        TreeSet treeSet = new TreeSet();
                        treeSet.addAll(names);



                        String uniqueName = "";

                        Iterator iterator = treeSet.iterator();
                        while (iterator.hasNext())
                        {
                            uniqueName = uniqueName + iterator.next();
                        }

                        Intent intent = new Intent(getContext(),ChatActivity.class);
                        intent.putExtra("uniqueName",uniqueName);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Locate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dbRef = firebaseDatabase.getReferenceFromUrl("https://stuconnect-152516.firebaseio.com/Users/"+uID);
                        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.e("abc",uniqueID.get(1).toString());
                                //dataSnapshot.child("Location_Details").getValue(LocDetails.class)
                                if(dataSnapshot.child("Location_Details").exists()) {
                                    LocDetails locDetails = dataSnapshot.child("Location_Details").getValue(LocDetails.class);
                                    latitude = locDetails.getLatitude();
                                    longitude = locDetails.getLongitude();
                                    date = locDetails.getDate();
                                    name = dataSnapshot.child("userName").getValue(String.class);
                                    Intent intent = new Intent(getContext(),MapsActivity.class);
                                    intent.putExtra("latitude",latitude);
                                    intent.putExtra("longitude",longitude);
                                    intent.putExtra("date",date);
                                    intent.putExtra("name",name);
                                    startActivityForResult(intent,12);
                                    //Toast.makeText(getContext(),locDetails.getLatitude() + '\n' + locDetails.getLongitude() ,Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getContext(),"No location details",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });

                builder.show();
                //get Latitude and Longitude from this Url
            }
        });

        return fragmentView;
    }



    public static Friends newInstance() {
        
        Bundle args = new Bundle();
        
        Friends fragment = new Friends();
        fragment.setArguments(args);
        return fragment;
    }
}
