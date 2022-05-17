package com.example.taxiapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxiapp.Model.Fare;
import com.example.taxiapp.R;
import com.example.taxiapp.databinding.FragmentHomeBinding;
import com.google.android.gms.location.LocationRequest;

import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    ProgressBar loadingIndicator;
    private FareViewModel fareViewModel;

    private ConstraintLayout pickupLayout;
    private ConstraintLayout destinationLayout;
    private RecyclerView rv;
    private TextView noActiveJobsTv;
    Button finishBt;




    private LocationRequest locationRequest;
    private String currentLocation = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fareViewModel = new ViewModelProvider(this).get(FareViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        View.OnClickListener onItemClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RecyclerView.ViewHolder vh = (RecyclerView.ViewHolder) view.getTag();
                int position = vh.getAdapterPosition();

            }
        };


        loadingIndicator = binding.jobLoadingIndicator;
        loadingIndicator.setVisibility(View.GONE);

        noActiveJobsTv = binding.noActiveJobsTv;
        rv = binding.rvJob;
        rv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        rv.setHasFixedSize(true);
        FareAdapter adapter = new FareAdapter(root);
        rv.setAdapter(adapter);



        fareViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading) loadingIndicator.setVisibility(View.VISIBLE);
                else loadingIndicator.setVisibility(View.GONE);
            }
        });
        fareViewModel.getFares().observe(getViewLifecycleOwner(), new Observer<List<Fare>>() {
            @Override
            public void onChanged(List<Fare> fares) {

                fareViewModel.getIsLoading().postValue(false);
                adapter.setActiveFares(fares);
                adapter.setOnItemClickListener(new FareAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Fare fare) {
                        Fare fareToChange = fares.stream().filter(f->f.getId() == fare.getId()).findFirst().orElse(null);
                        fare.setHasActive(false);
                        fares.set(fares.indexOf(fareToChange), fare);

                        fareViewModel.updateFares(fares);
                        adapter.setActiveFares(fares);
                        Log.d("Home fragment",fares.toString());

                        if(fares.stream().noneMatch(Fare::isHasActive)){
                            noActiveJobsTv.setText("No active jobs");
                        }
                        else{
                            noActiveJobsTv.setText("");
                        }
                    }

                }
                );
                if(fares.stream().noneMatch(Fare::isHasActive)){
                    noActiveJobsTv.setText("No active jobs");
                }
                else{
                    noActiveJobsTv.setText("");
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

    public void setNoActiveJobsText(String text){
        noActiveJobsTv.setText(text);
    }
}