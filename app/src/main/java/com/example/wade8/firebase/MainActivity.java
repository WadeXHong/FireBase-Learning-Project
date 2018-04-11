package com.example.wade8.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private EditText tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        tag = findViewById(R.id.tag);
    }


    public void AddArticleTest(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Date currentTime = Calendar.getInstance().getTime();
        DatabaseReference reference = database.getReference().child("Articles").push();
        reference.child("title").setValue(title.getText().toString());
        reference.child("author").setValue(FirebaseAuth.getInstance().getUid());
        reference.child("content").setValue(content.getText().toString());
        reference.child("tag").setValue(tag.getText().toString());
        reference.child("createdtime").setValue(currentTime.getTime());

    }

    public void FriendPage(View view) {
        Intent intent = new Intent(this,UserActivity.class);
        startActivity(intent);
    }
}
