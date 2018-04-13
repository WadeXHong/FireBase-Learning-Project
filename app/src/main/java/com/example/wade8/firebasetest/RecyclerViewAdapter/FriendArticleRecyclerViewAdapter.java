package com.example.wade8.firebasetest.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wade8.firebasetest.Article;
import com.example.wade8.firebasetest.R;

import java.util.ArrayList;

/**
 * Created by wade8 on 2018/4/11.
 */

public class FriendArticleRecyclerViewAdapter extends RecyclerView.Adapter<FriendArticleRecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<Article> arrayList;

    public FriendArticleRecyclerViewAdapter(ArrayList<Article> arrayList){
        this.arrayList = arrayList ;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_friendarticlerecyclerview,parent,false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView tag;
        private TextView content;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.friendarticletitle);
            tag = itemView.findViewById(R.id.friendarticletag);
            content = itemView.findViewById(R.id.friendarticlecontent);

        }
        public void bind (int position){
            if(arrayList != null){
                title.setText(arrayList.get(position).getTitle());
                tag.setText(arrayList.get(position).getTag());
                content.setText(arrayList.get(position).getContent());
            }
        }
    }


}
