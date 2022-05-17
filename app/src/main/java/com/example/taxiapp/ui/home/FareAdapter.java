package com.example.taxiapp.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxiapp.Model.Fare;
import com.example.taxiapp.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FareAdapter extends RecyclerView.Adapter<FareAdapter.FareViewHolder> {
    private List<Fare> activeFares = new ArrayList<>();
    private final int LIMIT = 1;
    private View root;
    private LocationRequest locationRequest;
    private String currentLocation = "";
    private OnItemClickListener listener;

    class FareViewHolder extends RecyclerView.ViewHolder {
        private TextView customerNameTv,
                destinationTv, pickupTv, distanceTv;
        private Button finishBt;
        private ImageView showPickup, showDestination;

        public FareViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            customerNameTv = itemView.findViewById(R.id.customer_name_tv);
            destinationTv = itemView.findViewById(R.id.destination_address);
            pickupTv = itemView.findViewById(R.id.pickup_address_tv);
            finishBt = itemView.findViewById(R.id.finish_job);
            showPickup = itemView.findViewById(R.id.show_route_to_pickup);
            showDestination = itemView.findViewById(R.id.show_route_to_destination);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(activeFares.get(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public FareAdapter.FareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate((R.layout.fare_item), parent, false);
        return new FareViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FareViewHolder holder, int position) {
        if (activeFares.stream().anyMatch(Fare::isHasActive)) {
            Log.d("Adapter", "fares contain active jobs");
            Fare currentFare = activeFares.get(position);

            if (currentFare.isHasActive()) {
                holder.customerNameTv.setText(currentFare.getName());
                holder.destinationTv.setText(currentFare.getDestinationAddress());
                holder.pickupTv.setText(currentFare.getPickupAddress());
                holder.showPickup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getLocation(root);
                        String start = currentLocation;
                        String destination = currentFare.getPickupAddress();

                        DisplayRoute(start, destination);
                    }
                });
                holder.showDestination.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getLocation(root);
                        String start = currentFare.getPickupAddress();
                        String destination = currentFare.getDestinationAddress();

                        DisplayRoute(start, destination);
                    }
                });

                holder.finishBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentFare.setHasActive(false);
                        activeFares.remove(currentFare);
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), activeFares.size());
                    }
                });
            }
        } else {
            activeFares.remove(position);
            notifyItemRemoved(holder.getAdapterPosition());
            notifyItemRangeChanged(holder.getAdapterPosition(), activeFares.size());
        }


    }

    @Override
    public int getItemCount() {
        if (activeFares.size() > LIMIT) {
            return LIMIT;
        } else {
            return activeFares.size();
        }
    }

    public void setActiveFares(List<Fare> fares) {
        //remove not active jobs
        this.activeFares = fares.stream().filter(fare -> fare.isHasActive()).collect(Collectors.toList());
        Log.d("FareAdapter", fares.stream().filter(fare -> !fare.isHasActive()).collect(Collectors.toList()).toString());
        Log.d("FareAdapter", activeFares.toString());
        notifyDataSetChanged();
    }

    public FareAdapter(View context) {
        this.root = context;

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
    }

    private void getLocation(View root) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(root.getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (isGPSEnabled(root)) {
                    LocationServices.getFusedLocationProviderClient(root.getContext())
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(root.getContext())
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() > 0) {

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();
                                        currentLocation = longitude + "," + latitude;

                                    }
                                }
                            }, Looper.myLooper());
                } else {
                    turnOnGPS(root);
                }
            }
        } else {
//            root.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Fare fare);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    private void DisplayRoute(String start, String destination) {
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + start + "/" + destination);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            root.getContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            root.getContext().startActivity(intent);
        }
    }

    private boolean isGPSEnabled(View root) {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) root.getContext().getSystemService(Context.LOCATION_SERVICE);
        }
        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }

    private void turnOnGPS(View root) {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(root.getContext().getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(root.getContext(), "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult((Activity) root.getContext(), 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }
}
