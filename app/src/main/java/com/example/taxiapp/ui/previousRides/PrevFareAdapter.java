package com.example.taxiapp.ui.previousRides;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxiapp.Model.Arrival;
import com.example.taxiapp.Model.Fare;
import com.example.taxiapp.R;
import com.example.taxiapp.ui.timetable.ArrivalsAdapter;

import java.util.ArrayList;
import java.util.List;

public class PrevFareAdapter extends RecyclerView.Adapter<PrevFareAdapter.HistoryViewHolder> {
    private List<Fare> fares = new ArrayList<>();
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prev_fare_list_item, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Fare currentFare = fares.get(position);
        holder.destinationTv.setText(currentFare.getDestinationAddress());
        holder.pickupTv.setText(currentFare.getPickupAddress());
        holder.customerNameTv.setText(currentFare.getName());
    }




    @Override
    public int getItemCount() {
        return fares.size();
    }

    public void setFares(List<Fare> fares) {
        this.fares = fares;
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView customerNameTv, pickupTv, destinationTv;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTv = itemView.findViewById(R.id.tvname_prev);
            pickupTv = itemView.findViewById(R.id.tvpickup_history);
            destinationTv = itemView.findViewById(R.id.tvDestination_history);
        }
    }
}
