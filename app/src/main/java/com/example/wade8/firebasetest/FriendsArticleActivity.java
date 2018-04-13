package com.example.wade8.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.wade8.firebasetest.RecyclerViewAdapter.FriendArticleRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendsArticleActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private ArrayList<Article> articleArrayList = new ArrayList<>();
    String friendUID;

    private RecyclerView recyclerView;
    private FriendArticleRecyclerViewAdapter recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_article);
        Intent intent = getIntent();
        friendUID = intent.getStringExtra("UID");
        Log.d(TAG, "friendUID : " + friendUID);

        recyclerView = findViewById(R.id.friendarticle_recyclerview);
        recyclerViewAdapter = new FriendArticleRecyclerViewAdapter(articleArrayList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query query = database.getReference().child("Articles").orderByChild("author").equalTo(friendUID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"Article onDataChange excuted");
                if(articleArrayList.size()>0) articleArrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Article article = snapshot.getValue(Article.class);
                    articleArrayList.add(article);
                    Log.d(TAG,"author : "+ article.getAuthor());
                    Log.d(TAG,"title :"+ article.getTitle());
                    Log.d(TAG,"content :"+ article.getContent());
                    Log.d(TAG,"createdTime :"+ article.getCreatedTime());
                    Log.d(TAG,"tag :"+ article.getTag());
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

}
