package com.example.wade8.firebase.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wade8.firebase.Friend;
import com.example.wade8.firebase.R;
import com.example.wade8.firebase.Request;
import com.example.wade8.firebase.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by wade8 on 2018/4/10.
 */

public class FriendListRecyclerViewAdapter extends RecyclerView.Adapter<FriendListRecyclerViewAdapter.RecyclerViewHolder>{


    private final ArrayList<Friend> friendListArrayList;

    public FriendListRecyclerViewAdapter(ArrayList<Friend> friendListArrayList) {
        this.friendListArrayList = friendListArrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_friendlistrecyclerview,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return friendListArrayList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView friendUID;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            friendUID = itemView.findViewById(R.id.friend_UID);
        }

        private void bind (int position){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference().child("User").child(friendListArrayList.get(position).getFriendUID()).child("email");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())friendUID.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            friendUID.setText(friendListArrayList.get(position).getFriendUID());
        }

    }
}
