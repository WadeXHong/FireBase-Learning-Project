package com.example.wade8.firebase.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wade8.firebase.Request;
import com.example.wade8.firebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by wade8 on 2018/4/10.
 */

public class RequestListRecyclerViewAdapter extends RecyclerView.Adapter<RequestListRecyclerViewAdapter.RecyclerViewHolder>{

    private final String TAG = getClass().getSimpleName();

    private final ArrayList<Request> requestArrayList;

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
                requestUser.setText(requestArrayList.get(position).getUID());
                switch (requestArrayList.get(position).getRequest_status()){
                    case "send":
                    case "reject":
                    case "accept":
                        requestStatus.setVisibility(View.VISIBLE);
                        requestStatus.setText("Status: "+ requestArrayList.get(position).getRequest_status());
                        acceptRequest.setVisibility(View.INVISIBLE);
                        denyRequest.setVisibility(View.INVISIBLE);
                        break;

                    case "received":
                        requestStatus.setVisibility(View.INVISIBLE);
                        acceptRequest.setVisibility(View.VISIBLE);
                        denyRequest.setVisibility(View.VISIBLE);
                        acceptRequest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String otherUID = requestArrayList.get(position).getUID();
                                String myUID = FirebaseAuth.getInstance().getUid();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference().child("Friend");
                                reference.child(myUID).child(otherUID).child("request_status").setValue("added");
                                reference.child(otherUID).child(myUID).child("request_status").setValue("added");
                                Log.d(TAG,"Accept " + otherUID);
                            }
                        });
                        denyRequest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String otherUID = requestArrayList.get(position).getUID();
                                String myUID = FirebaseAuth.getInstance().getUid();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference().child("Friend");
                                reference.child(myUID).child(otherUID).child("request_status").removeValue();
                                reference.child(otherUID).child(myUID).child("request_status").setValue("reject");
                                Log.d(TAG,"Reject " + otherUID);
                            }
                        });
                        break;
                }

            }
        }

    }
}
