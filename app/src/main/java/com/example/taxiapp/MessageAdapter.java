package com.example.taxiapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    Context context;
    List<Message> messagesArrayList;
    private FirebaseAuth fAuth;

    public MessageAdapter(Context context, List<Message> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
        fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context)
                .inflate(R.layout.message_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Message message = messagesArrayList.get(position);
        if(message.getName().equals(fAuth.getCurrentUser().getUid()))
        holder.itemView.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        holder.date.setText(message.getDate());
        holder.message.setText(message.getMessage());
        holder.user.setText(message.getName());
        holder.time.setText(message.getTime());
        String toPrint = message.getUserId();
        Log.d("messageAdp", toPrint);
        Log.d("messageAdp",fAuth.getUid());


    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    public void setMessagesArrayList(ArrayList<Message> messagesArrayList) {
        this.messagesArrayList = messagesArrayList;
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
