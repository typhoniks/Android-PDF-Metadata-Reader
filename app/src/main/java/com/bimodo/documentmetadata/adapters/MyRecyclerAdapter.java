package com.bimodo.documentmetadata.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bimodo.documentmetadata.R;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    Context context;
    String[] metadataNames = {"Producer", "Title", "Keywords", "Author", "Subject", "Creation Date", "Modification Date"};
    String[] metadataInfo;

    public MyRecyclerAdapter(Context c, String[] mdInfo) {
        this.context = c;
        this.metadataInfo = mdInfo;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.info_item, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.metaDataName.setText(metadataNames[position]);

        String metadata = metadataInfo[position];
        if (metadata == null || metadata.equals("null") || metadata == " ") {
            metadata = "Field is empty";
            holder.metaDataInfo.setText(metadata);

        } else {
            holder.metaDataInfo.setText(metadata);
        }

    }

    @Override
    public int getItemCount() {
        return metadataInfo.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView metaDataInfo,metaDataName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            metaDataInfo = itemView.findViewById(R.id.metaDataInfo);
            metaDataName = itemView.findViewById(R.id.metadataName);

        }
    }

}

