package com.example.wade8.firebase.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wade8.firebase.R;
import com.example.wade8.firebase.User;

import java.util.ArrayList;

/**
 * Created by wade8 on 2018/4/10.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{


    private final ArrayList<User> userArrayList;

    public RecyclerViewAdapter(ArrayList<User> userArrayList) {
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

        private TextView userEmail;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            userEmail = itemView.findViewById(R.id.user_email);
        }

        private void bind (int position){
            userEmail.setText(userArrayList.get(position).getEmail());
        }

    }
}
