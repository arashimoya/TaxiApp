package com.example.taxiapp.ui.timetable;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxiapp.Arrival;
import com.example.taxiapp.ArrivalsAdapter;
import com.example.taxiapp.NavigationActivity;
import com.example.taxiapp.R;
import com.example.taxiapp.StopLocation;
import com.example.taxiapp.databinding.FragmentTimetableBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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


        locationNames = new ArrayList<>();
        seedStops();
        editText = binding.actv;
        ArrayAdapter<String> strAdapter = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_list_item_1,
                locationNames.stream().map(StopLocation::getStop).collect(Collectors.toList()));
        editText.setAdapter(strAdapter);



        final TextView textView = binding.textTimetable;
        timetableViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        final TextView arrivalsText = binding.textTimetable;


        final Button searchButton = binding.searchArrivalsButton;

        recyclerView = binding.rv;
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        ArrivalsAdapter adapter = new ArrivalsAdapter();
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id=  Objects.requireNonNull(locationNames.stream().filter(stopLocation -> stopLocation.getStop().equals(editText.getText().toString())).findFirst().orElse(null)).getId();
                timetableViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isLoading) {
                        if(isLoading) loadingIndicator.setVisibility(View.VISIBLE);
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
        });



        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private void seedStops(){
        locationNames.add(new StopLocation(8600626, "Kobenhavn H") );
        locationNames.add(new StopLocation(8600040, "Randers St.") );
        locationNames.add(new StopLocation(8600066, "Horsens St.") );
        locationNames.add(new StopLocation(8600001, "Frederikshavn") );
        locationNames.add(new StopLocation(8600053, "Aarhus H") );
        locationNames.add(new StopLocation(8600079, "Fredericia St.") );
        locationNames.add(new StopLocation(8600327, "Sonderborg") );
        locationNames.add(new StopLocation(8600215, "Esbjerg st.") );
        locationNames.add(new StopLocation(8600020, "Aalborg") );

    }

}