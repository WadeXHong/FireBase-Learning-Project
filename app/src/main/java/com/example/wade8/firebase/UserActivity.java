package com.example.wade8.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.wade8.firebase.RecyclerViewAdapter.FriendListRecyclerViewAdapter;
import com.example.wade8.firebase.RecyclerViewAdapter.RequestListRecyclerViewAdapter;
import com.example.wade8.firebase.RecyclerViewAdapter.UserRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private ArrayList<User> userArrayList = new ArrayList<>();
    private ArrayList<Request> requestArrayList = new ArrayList<>();
    private ArrayList<String> friendEmailArrayList = new ArrayList<>();
    private ArrayList<String> friendUIDArrayList = new ArrayList<>();

    private RecyclerView userRecyclerView;
    private UserRecyclerViewAdapter userRecyclerViewAdapter;
    private LinearLayoutManager userLinearLayoutManager;

    private RecyclerView friendRecyclerView;
    private FriendListRecyclerViewAdapter friendListRecyclerViewAdapter;
    private LinearLayoutManager friendListLinearLayoutManager;

    private RecyclerView requestRecyclerView;
    private RequestListRecyclerViewAdapter requestListRecyclerViewAdapter;
    private LinearLayoutManager requestListLinearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //user
        DatabaseReference userReference = database.getReference().child("User");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"onDataChange excuted");
                if(userArrayList.size()>0)userArrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    user.setUID(snapshot.getKey());
                    userArrayList.add(user);
                    Log.d(TAG,"UID : "+ user.getUID());
                    Log.d(TAG,"email :"+ user.getEmail());
                }
                userRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });


        //request
        DatabaseReference requestReference = database.getReference().child("Friend").child(FirebaseAuth.getInstance().getUid());
        requestReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG,"onDataChange excuted");
                if(requestArrayList.size()>0) requestArrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Request request = snapshot.getValue(Request.class);
                    request.setUID(snapshot.getKey());
                    requestArrayList.add(request);
                    Log.d(TAG,"request otherside UID : "+ request.getUID());
                    Log.d(TAG,"request otherside UID : "+ request.getRequest_status());
                }
                requestListRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //friend
        DatabaseReference friendReference = database.getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("Friend");
        friendReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"onDataChange excuted");
                if(friendEmailArrayList.size()>0) {
                    friendEmailArrayList.clear();
                    friendUIDArrayList.clear();
                }
                    Map<String,String> friend = (HashMap<String,String>)dataSnapshot.getValue();
                    Object[] values = friend.values().toArray();
                    Object[] keys = friend.values().toArray();
                    for(int i=0 ; i<values.length;i++){
                        friendEmailArrayList.add((String) values[i]);
                        friendUIDArrayList.add((String)keys[i]);
                    }
                friendListRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        //friend
//        final DatabaseReference friendReference = database.getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("Friend");
//        requestReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Log.d(TAG,"onDataChange excuted");
//                if(friendEmailArrayList.size()>0) friendEmailArrayList.clear();
//                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
//                    Friend friend = snapshot.getValue(Friend.class);
//                    friend
//                    friendEmailArrayList.add(friend);
//                    Log.d(TAG,"request otherside UID : "+ request.getUID());
//                    Log.d(TAG,"request otherside UID : "+ request.getRequest_status());
//                }
//                requestListRecyclerViewAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



        //userRecyclerView
        userRecyclerView = findViewById(R.id.user_recyclerview);
        userLinearLayoutManager = new LinearLayoutManager(this);
        userRecyclerView.setLayoutManager(userLinearLayoutManager);
        userRecyclerViewAdapter = new UserRecyclerViewAdapter(userArrayList);
        userRecyclerView.setAdapter(userRecyclerViewAdapter);


        //requestRecyclerView
        requestRecyclerView = findViewById(R.id.frindrequest_recyclerview);
        requestListLinearLayoutManager = new LinearLayoutManager(this);
        requestRecyclerView.setLayoutManager(requestListLinearLayoutManager);
        requestListRecyclerViewAdapter = new RequestListRecyclerViewAdapter(requestArrayList);
        requestRecyclerView.setAdapter(requestListRecyclerViewAdapter);

        //requestRecyclerView
        friendRecyclerView = findViewById(R.id.friend_recyclerview);
        friendListLinearLayoutManager = new LinearLayoutManager(this);
        friendRecyclerView.setLayoutManager(friendListLinearLayoutManager);
        friendListRecyclerViewAdapter = new FriendListRecyclerViewAdapter(friendEmailArrayList);
        friendRecyclerView.setAdapter(friendListRecyclerViewAdapter);
    }
}
