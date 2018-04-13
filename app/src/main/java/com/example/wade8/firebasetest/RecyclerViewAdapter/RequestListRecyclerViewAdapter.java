package com.example.wade8.firebasetest.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wade8.firebasetest.Request;
import com.example.wade8.firebasetest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by wade8 on 2018/4/10.
 */

public class RequestListRecyclerViewAdapter extends RecyclerView.Adapter<RequestListRecyclerViewAdapter.RecyclerViewHolder>{

    private final String TAG = getClass().getSimpleName();

    private ArrayList<Request> requestArrayList;

    private String email;

    public RequestListRecyclerViewAdapter(ArrayList<Request> requestArrayList) {
        this.requestArrayList = requestArrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_requestlistrecyclerview,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView requestUser;
        private Button acceptRequest;
        private Button denyRequest;
        private TextView requestStatus;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            requestUser = itemView.findViewById(R.id.request_email);
            acceptRequest = itemView.findViewById(R.id.request_accept);
            denyRequest = itemView.findViewById(R.id.request_deny);
            requestStatus = itemView.findViewById(R.id.request_status);
        }

        private void bind (final int position){
            if (requestArrayList != null) {

                //Firebase Database 初始化
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference requestRef = database.getReference().child("Friend");
                final DatabaseReference friendListRef = database.getReference().child("FriendList");
                final DatabaseReference ref = database.getReference().child("User");

                final String otherUID = requestArrayList.get(position).getUID();
                final String myUID = FirebaseAuth.getInstance().getUid();

                //改以email顯示
                ref.child(requestArrayList.get(position).getUID()).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            email = dataSnapshot.getValue().toString();
                            requestUser.setText(email);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //按鈕顯示行為
                switch (requestArrayList.get(position).getRequest_status()){
                    case "send":
                        requestStatus.setVisibility(View.VISIBLE);
                        requestStatus.setText("Status: "+ requestArrayList.get(position).getRequest_status());
                        acceptRequest.setVisibility(View.INVISIBLE);
                        denyRequest.setVisibility(View.INVISIBLE);
                        break;

                    case "reject":
//                        requestStatus.setVisibility(View.VISIBLE);
//                        requestStatus.setText("Status: "+ requestArrayList.get(position).getRequest_status());
//                        acceptRequest.setVisibility(View.INVISIBLE);
//                        denyRequest.setVisibility(View.INVISIBLE);
                        requestRef.child(myUID).child(otherUID).child("request_status").removeValue();
                        requestRef.child(otherUID).child(myUID).child("request_status").removeValue();
                        break;

                    case "accept":
//                        requestStatus.setVisibility(View.VISIBLE);
//                        requestStatus.setText("Status: "+ requestArrayList.get(position).getRequest_status());
//                        acceptRequest.setVisibility(View.INVISIBLE);
//                        denyRequest.setVisibility(View.INVISIBLE);
                        requestRef.child(myUID).child(otherUID).child("request_status").removeValue();
                        requestRef.child(otherUID).child(myUID).child("request_status").removeValue();
                        break;

                    case "received":
                        requestStatus.setVisibility(View.INVISIBLE);
                        acceptRequest.setVisibility(View.VISIBLE);
                        denyRequest.setVisibility(View.VISIBLE);
                        acceptRequest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ref.child(myUID).child("Friend").child(otherUID).setValue(email);
//                                friendListRef.child(myUID).child(otherUID).child("isFriend").setValue(true);
                                ref.child(otherUID).child("Friend").child(myUID).setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
//                                friendListRef.child(otherUID).child(myUID).child("isFriend").setValue(true);
                                requestRef.child(myUID).child(otherUID).child("request_status").removeValue();
                                requestRef.child(otherUID).child(myUID).child("request_status").removeValue();
                                Log.d(TAG,"Accept " + otherUID);
                            }
                        });
                        denyRequest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String otherUID = requestArrayList.get(position).getUID();
                                String myUID = FirebaseAuth.getInstance().getUid();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                requestRef.child(myUID).child(otherUID).child("request_status").removeValue();
                                requestRef.child(otherUID).child(myUID).child("request_status").removeValue();
                                Log.d(TAG,"Reject " + otherUID);
                            }
                        });
                        break;
                }

            }
        }

    }
}
