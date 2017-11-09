package com.dev4tomorrow.edwinkato.bucketlist.adapter;

/**
 * Created by edwinkato on 10/18/17.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev4tomorrow.edwinkato.bucketlist.R;
import com.dev4tomorrow.edwinkato.bucketlist.data.model.BucketList;

import java.util.List;

public class BucketListsAdapter extends RecyclerView.Adapter<BucketListsAdapter.MyViewHolder> {

    private List<BucketList> bucketLists;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
        }
    }


    public BucketListsAdapter(List<BucketList> bucketLists) {
        this.bucketLists = bucketLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bucket_list_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BucketList bucketList = bucketLists.get(position);
        holder.title.setText(bucketList.getTitle());
        holder.description.setText(bucketList.getDescription());
    }

    @Override
    public int getItemCount() {
        return bucketLists.size();
    }
}
