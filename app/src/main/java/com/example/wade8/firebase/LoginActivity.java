package com.example.wade8.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    //Login
    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private String email;
    private String password;

    //Database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_ET);
        passwordEditText = findViewById(R.id.password_ET);



        mAuth = FirebaseAuth.getInstance();





    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){
            Log.d(TAG,"currentUser : " + currentUser.toString());
            Log.d(TAG,"my UID : " + mAuth.getInstance().getUid());
            goToUserActivity();
        }
    }

    private void goToUserActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void SignUP(final View view) {

        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(email, password)
                  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {
                              // Sign in success, update UI with the signed-in user's information
                              Log.d(TAG, "createUserWithEmail:success");
                              FirebaseUser user = mAuth.getCurrentUser();
                              WriteToDataBase();
                              goToUserActivity();
//                              updateUI(user);
                          } else {
                              // If sign in fails, display a message to the user.
                              Log.w(TAG, "createUserWithEmail:failure", task.getException());
                              Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                              updateUI(null);
                          }

                          // ...
                      }
                  });
    }

    public void Login(View view){
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, password)
                  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {
                              // Sign in success, update UI with the signed-in user's information
                              Log.d(TAG, "signInWithEmail:success");
                              FirebaseUser user = mAuth.getCurrentUser();
                              goToUserActivity();
//                              updateUI(user);
                          } else {
                              // If sign in fails, display a message to the user.
                              Log.w(TAG, "signInWithEmail:failure", task.getException());
                              Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                              updateUI(null);
                          }

                          // ...
                      }
                  });
    }

    public void WriteToDataBase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference user = database.getReference("User").child(mAuth.getUid()).child("email");
        user.setValue(email);
    }

    public void AddArticleTest(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Date currentTime = Calendar.getInstance().getTime();
        DatabaseReference reference = database.getReference().child("Articles").push();
        reference.child("title").setValue("life");
        reference.child("author").setValue(mAuth.getUid());
        reference.child("content").setValue("is a");
        reference.child("tag").setValue("struggle");
        reference.child("createdtime").setValue(currentTime.getTime());

    }
}
