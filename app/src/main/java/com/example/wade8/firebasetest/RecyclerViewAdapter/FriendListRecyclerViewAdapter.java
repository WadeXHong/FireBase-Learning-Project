package com.example.wade8.firebasetest.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wade8.firebasetest.FriendsArticleActivity;
import com.example.wade8.firebasetest.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by wade8 on 2018/4/10.
 */

public class FriendListRecyclerViewAdapter extends RecyclerView.Adapter<FriendListRecyclerViewAdapter.RecyclerViewHolder>{


    private ArrayList<String> friendListEmailArrayList;
    private ArrayList<String> friendListUIDArrayList;

    public FriendListRecyclerViewAdapter(ArrayList<String> friendListEmailArrayList, ArrayList<String> friendListUIDArrayList) {
        this.friendListEmailArrayList = friendListEmailArrayList;
        this.friendListUIDArrayList = friendListUIDArrayList;
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
        return friendListEmailArrayList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView friendUID;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            friendUID = itemView.findViewById(R.id.friend_UID);
            itemView.setOnClickListener(this);

        }

        private void bind (int position){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference ref = database.getReference().child("User").child(friendListEmailArrayList.get(position).getFriendUID()).child("email");
//            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists())friendUID.setText(dataSnapshot.getValue().toString());
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
            friendUID.setText(friendListEmailArrayList.get(position));
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), FriendsArticleActivity.class);
            intent.putExtra("UID",friendListUIDArrayList.get(getLayoutPosition()));
            v.getContext().startActivity(intent);
        }
    }
}
