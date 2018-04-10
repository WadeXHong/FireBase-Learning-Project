package com.example.wade8.firebase.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wade8.firebase.R;
import com.example.wade8.firebase.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wade8 on 2018/4/10.
 */

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.RecyclerViewHolder>{

    private final String TAG = getClass().getSimpleName();
    private final ArrayList<User> userArrayList;

    public UserRecyclerViewAdapter(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_userrecyclerview,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private boolean isFriend = false ;
        private TextView userEmail;
        private Button sendRequest;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            userEmail = itemView.findViewById(R.id.user_email);
            sendRequest = itemView.findViewById(R.id.user_sendrequest);
        }

        private void bind (final int position){

            if(userArrayList != null) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference friendListRef = database.getReference().child("FriendList");
                userEmail.setText(userArrayList.get(position).getEmail());

                friendListRef.child(FirebaseAuth.getInstance().getUid())
                          .child(userArrayList.get(position).getUID())
                          .child("isFriend").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())isFriend = (boolean) dataSnapshot.getValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                if (userArrayList.get(position).getUID().equals(FirebaseAuth.getInstance().getUid())|isFriend){
                    sendRequest.setVisibility(View.INVISIBLE);
                }else {
                    sendRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String otherUID = userArrayList.get(position).getUID();
                            String myUID = FirebaseAuth.getInstance().getUid();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference().child("Friend");
                            reference.child(myUID).child(otherUID).child("request_status").setValue("send");
                            reference.child(otherUID).child(myUID).child("request_status").setValue("received");
                        }
                    });
                }
            }
        }

    }
}
