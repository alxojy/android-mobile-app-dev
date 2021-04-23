package com.fit2081.chatapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<String> data = new ArrayList<>();

    public ChatAdapter(ArrayList<String> data) {
        this.data = data;
        Log.d("Week8ChatApp","init Adapter");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Week8ChatApp","Create View");

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.msg_card, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("Week8ChatApp","Bind View  Adapter");

        holder.messageTv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView messageTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTv = itemView.findViewById(R.id.msg_tv_id);
        }
    }
}
