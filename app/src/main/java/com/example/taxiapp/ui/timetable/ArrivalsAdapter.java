package com.example.taxiapp.ui.timetable;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxiapp.Model.Arrival;
import com.example.taxiapp.R;

import java.util.ArrayList;
import java.util.List;

public class ArrivalsAdapter  extends RecyclerView.Adapter<ArrivalsAdapter.ViewHolder> {

    private List<Arrival> arrivals = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView timeTv;
        private TextView originTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.tvTime);
            originTv = itemView.findViewById(R.id.tvOrigin);
        }
    }

    @NonNull
    @Override
    public ArrivalsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.arrival_list_item, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ArrivalsAdapter.ViewHolder holder, int position) {
        Arrival currentArrival = arrivals.get(position);
        holder.originTv.setText(currentArrival.getOrigin());
        holder.timeTv.setText(currentArrival.getTime());

        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#e3e3e3"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
    }

    @Override
    public int getItemCount() {
        return arrivals.size();
    }

    public void setArrivals(List<Arrival> arrivals){
        this.arrivals = arrivals;
        notifyDataSetChanged();
    }
}
