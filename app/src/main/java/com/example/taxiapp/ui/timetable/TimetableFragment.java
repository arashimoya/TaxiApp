package com.example.taxiapp.ui.timetable;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxiapp.Model.Arrival;
import com.example.taxiapp.Model.StopLocation;
import com.example.taxiapp.databinding.FragmentTimetableBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimetableFragment extends Fragment {

    private TimetableViewModel timetableViewModel;
    RecyclerView recyclerView;
    ProgressBar loadingIndicator;
    AutoCompleteTextView editText;
    private List<StopLocation> locationNames;


    private FragmentTimetableBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TimetableViewModel timetableViewModel =
                new ViewModelProvider(this).get(TimetableViewModel.class);

        binding = FragmentTimetableBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        loadingIndicator = binding.arrivalLoadingIndicator;
        loadingIndicator.setVisibility(View.GONE);

        final Button searchButton = binding.searchArrivalsButton;
        locationNames = new ArrayList<>();
        List<String> locationsInString = new ArrayList<>();
        timetableViewModel.getAllStops().observe(getViewLifecycleOwner(), new Observer<List<StopLocation>>() {
            @Override
            public void onChanged(List<StopLocation> stops) {
                if(stops.isEmpty()){
                    timetableViewModel.populateStops();
                }
                locationNames.addAll(stops);
                Log.d("TimetableFG", locationNames.get(0).toString());
                for (StopLocation stop : locationNames) {
                    locationsInString.add(stop.getStop());
                }
            }
        });

        editText = binding.actv;
        ArrayAdapter<String> strAdapter = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_list_item_1, locationsInString
        );
        editText.setAdapter(strAdapter);


        final TextView textView = binding.textTimetable;
        timetableViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        final TextView arrivalsText = binding.textTimetable;


        recyclerView = binding.rv;
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        ArrivalsAdapter adapter = new ArrivalsAdapter();
        recyclerView.setAdapter(adapter);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText.getText().toString().matches("")) {
                    Toast.makeText(getContext(), "Please enter some characters first", Toast.LENGTH_SHORT).show();
                } else {
                    int id = Objects.requireNonNull(locationNames.stream().filter(stopLocation -> stopLocation.getStop().equals(editText.getText().toString())).findFirst().orElse(null)).getId();
                    timetableViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean isLoading) {
                            if (isLoading) loadingIndicator.setVisibility(View.VISIBLE);
                            else loadingIndicator.setVisibility(View.GONE);
                        }
                    });
                    timetableViewModel.getArrivalLiveData(id).observe(getViewLifecycleOwner(), new Observer<List<Arrival>>() {
                        @Override
                        public void onChanged(List<Arrival> arrivals) {
                            timetableViewModel.getIsLoading().postValue(false);
                            adapter.setArrivals(arrivals);
                        }
                    });
                }
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}