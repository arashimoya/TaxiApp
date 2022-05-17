package com.example.taxiapp.ui.previousRides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxiapp.Model.Arrival;
import com.example.taxiapp.Model.Fare;
import com.example.taxiapp.databinding.FragmentHistoryBinding;
import com.example.taxiapp.databinding.FragmentSettingsBinding;
import com.example.taxiapp.ui.settings.SettingsViewModel;
import com.example.taxiapp.ui.timetable.ArrivalsAdapter;

import java.util.List;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private RecyclerView recyclerView;
    ProgressBar loadingIndicator;
    private TextView jobCount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistoryViewModel historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadingIndicator = binding.arrivalLoadingIndicator;
        loadingIndicator.setVisibility(View.GONE);

        jobCount  = binding.jobCountTv;

        recyclerView = binding.rv;
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        PrevFareAdapter adapter = new PrevFareAdapter();
        recyclerView.setAdapter(adapter);

        historyViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading) loadingIndicator.setVisibility(View.VISIBLE);
                else loadingIndicator.setVisibility(View.GONE);
            }
        });
        historyViewModel.getFaresLiveData().observe(getViewLifecycleOwner(), new Observer<List<Fare>>() {
            @Override
            public void onChanged(List<Fare> fares) {
                historyViewModel.getIsLoading().postValue(false);
                adapter.setFares(fares);
                jobCount.setText(String.valueOf(fares.size()));
            }
        });


        return root;

    }
}
