package com.example.taxiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    Context context;
    ArrayList<Message> messagesArrayList;

    public MessageAdapter(Context context, ArrayList<Message> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.message_item,parent,false);



        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Message message = messagesArrayList.get(position);

        holder.date.setText(message.getDate());
        holder.message.setText(message.getMessage());
        holder.user.setText(message.getName());
        holder.time.setText(message.getTime());

    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView date, message, user, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tvdatedate);
            message = itemView.findViewById(R.id.tvmessage);
            user = itemView.findViewById(R.id.tvuser);
            time = itemView.findViewById(R.id.tvhourandminutes);
        }
    }
}
