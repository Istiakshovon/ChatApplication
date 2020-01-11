package com.istiak.chatapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;

    ArrayList<HashMap<String, String>> list;

    //constructor
    public CustomAdapter(Context context,  ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the item Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtMsg.setText(list.get(position).get(Constant.KEY_MSG));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtMsg,txtName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMsg = itemView.findViewById(R.id.txt_msg);
            txtName = itemView.findViewById(R.id.txt_name);
            txtMsg.setEnabled(false);
        }
    }
}
