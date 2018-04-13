package com.example.wade8.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private EditText title;
    private EditText content;
    private EditText tag;

    private EditText starttime;
    private EditText endtime;
    private Button orderbytime;

    private EditText findbytag;
    private Button findbytagBT;

    private Random r;

    private ArrayList<Article> articleArrayList = new ArrayList<>();
    private ArrayList<zArticles> zArticleArrayList = new ArrayList<>();

    private String authorName;
    private String authorImageUrl;
    private String authorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        tag = findViewById(R.id.tag);

        starttime = findViewById(R.id.starttime);
        endtime = findViewById(R.id.endtime);
        orderbytime = findViewById(R.id.timeorderBT);

        findbytag = findViewById(R.id.findbytag);
        findbytagBT = findViewById(R.id.findbytagBT);

        r = new Random(); //random
    }


    public void AddArticleTest(View view){

        String alphabet = "abcdefghijklmbopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String randomContent = "";
        String randomTitle = "";
        String randomUrl = "http://87.88.com/";
        String randomTag ="";

        for (int i = 0; i < 50; i++) {
            randomContent += alphabet.charAt(r.nextInt(60));
            randomUrl += alphabet.charAt(r.nextInt(60));
        }
        for (int i = 0; i < 20; i++) {
            randomTag += alphabet.charAt(r.nextInt(60));
            randomTitle += alphabet.charAt(r.nextInt(60));
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //TODO 測試階段:目前為從zUser拿假資料 需改為從CurrentUser.get 拿
        DatabaseReference authorDataTemp = database.getReference().child("zUser").child(FirebaseAuth.getInstance().getUid()).child("profile");
        authorDataTemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                authorName = (String) dataSnapshot.child("name").getValue();
                authorEmail = (String) dataSnapshot.child("email").getValue();
                authorImageUrl = (String) dataSnapshot.child("imageUrl").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        Date currentTime = Calendar.getInstance().getTime();
        DatabaseReference reference = database.getReference().child("zArticlesTest").push();
        reference.child(Constants.ARTICLE_CREATEDTIME).setValue(currentTime.getTime());
//        reference.child("content").setValue(content.getText().toString());
//        reference.child("title").setValue(title.getText().toString());
        reference.child(Constants.ARTICLE_CONTENT).setValue(randomContent);
        reference.child(Constants.ARTICLE_TITLE).setValue(randomTitle);
        reference.child(Constants.ARTICLE_PICTURE).setValue(randomUrl);
//        reference.child("tag").setValue(randomTag);
        reference.child(Constants.ARTICLE_TAG).setValue("AppWorkSchool");
        reference.child(Constants.ARTICLE_AUTHOR_ID).setValue(FirebaseAuth.getInstance().getUid());
        reference.child(Constants.ARTICLE_AUTHOR_NAME).setValue(authorName);
        reference.child(Constants.ARTICLE_AUTHOR_EMAIL).setValue(authorEmail);
        reference.child(Constants.ARTICLE_AUTHOR_IMAGE).setValue(authorImageUrl);
        reference.child(Constants.ARTICLE_INTERESTS).setValue(87);


    }

    public void FriendPage(View view) {
        Intent intent = new Intent(this,UserActivity.class);
        startActivity(intent);
    }

    public void OrderbyTime(View view) {

        int start = Integer.valueOf(starttime.getText().toString());
        int end = Integer.valueOf(endtime.getText().toString());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query query = database.getReference().child("Articles").orderByChild("createdTime").limitToFirst(start);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
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
//                userRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

    }
    public void FindByTag(View view){

        String tagInput = findbytag.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query query = database.getReference().child("Articles").orderByChild("tag").equalTo(tagInput);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
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
//                userRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    public void getArticle(View view){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = firebaseDatabase.getReference().child("zArticlesTest").orderByChild(Constants.ARTICLE_CREATEDTIME);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"Article onDataChange excuted");
                if(zArticleArrayList.size()>0) zArticleArrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    zArticles article = snapshot.getValue(zArticles.class);
                    zArticleArrayList.add(article);

                    Log.d(TAG,"i = " + zArticleArrayList.size());
                    Log.d(TAG,"articleId : "+ snapshot.getKey());
                    Log.d(TAG,"authorId : "+ article.authorId);
                    Log.d(TAG,"authorName : "+ article.authorName);
                    Log.d(TAG,"authorEmail : "+ article.authorEmail);
                    Log.d(TAG,"authorImage : "+ article.authorImage);
                    Log.d(TAG,"title :"+ article.title);
                    Log.d(TAG,"content :"+ article.content);
                    Log.d(TAG,"createdTime :"+ article.createdTime);
                    Log.d(TAG,"picture :"+ article.picture);
                    Log.d(TAG,"tag :"+ article.tag);
                    Log.d(TAG,"interestes :"+ article.interests);
                    Log.e(TAG,"分隔線---------------------------------------");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
