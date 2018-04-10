package com.example.wade8.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.wade8.firebase.RecyclerViewAdapter.RecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private ArrayList<User> userArrayList = new ArrayList<>();
    private ArrayList<FriendRequest> friendRequestArrayList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
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
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        DatabaseReference friendReference = database.getReference().child("Friend").child(FirebaseAuth.getInstance().getUid());
        friendReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG,"onDataChange excuted");
                if(friendRequestArrayList.size()>0)friendRequestArrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    FriendRequest friendRequest = snapshot.getValue(FriendRequest.class);
                    friendRequest.setUID(snapshot.getKey());
                    friendRequestArrayList.add(friendRequest);
                    Log.d(TAG,"friendRequest otherside UID : "+ friendRequest.getUID());
                    Log.d(TAG,"friendRequest otherside UID : "+ friendRequest.getRequest_status());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        recyclerView = findViewById(R.id.user_recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(userArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
