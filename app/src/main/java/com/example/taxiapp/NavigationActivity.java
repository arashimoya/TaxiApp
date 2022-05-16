package com.example.taxiapp;

import android.content.ActivityNotFoundException;

import android.content.Intent;

import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taxiapp.databinding.ActivityNavigationBinding;
import com.google.firebase.auth.FirebaseAuth;


public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;

    private TextView pickupText;
    private TextView destinationText;
    private TextView customerNameText;
    private Button getFareButton;
    private Button showPickupButton;
    private Button showDestinationButton;
    private FareViewModel fareViewModel;
    private Button logout;

    private ConstraintLayout pickupLayout;
    private ConstraintLayout destinationLayout;

    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigation.toolbar);
        binding.appBarNavigation.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_timetable, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        pickupLayout = findViewById(R.id.pickup_layout);
        destinationLayout = findViewById(R.id.destination_layout);
        pickupLayout.setVisibility(View.GONE);
        destinationLayout.setVisibility(View.GONE);

        pickupText = findViewById(R.id.pickup_text);
        destinationText = findViewById(R.id.destination_text);
        customerNameText = findViewById(R.id.customer_name_text);
        showPickupButton = findViewById(R.id.pickup_button);
        showDestinationButton = findViewById(R.id.destination_button);
        logout = findViewById(R.id.logout);

        fareViewModel = new ViewModelProvider(this).get(FareViewModel.class);
        fareViewModel.searchForFare();
        fareViewModel.getFare().observe(this, fare -> {
            pickupText.setText(fare.getPickupAddress());
            destinationText.setText(fare.getDestinationAddress());
            customerNameText.setText(fare.getName());
            destinationLayout.setVisibility(View.VISIBLE);
            pickupLayout.setVisibility(View.VISIBLE);

        });

        showPickupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = "Banegardsgade 2, 8700 Horsens";
                String destination = pickupText.getText().toString().trim();

                DisplayRoute(start,destination);
            }
        });

        showDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = pickupText.getText().toString().trim();
                String destination = destinationText.getText().toString().trim();

                DisplayRoute(start,destination);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    private void DisplayRoute(String start, String destination) {
        try{
           Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + start + "/" + destination);
           Intent intent = new Intent(Intent.ACTION_VIEW, uri);
           intent.setPackage("com.google.android.apps.maps");
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intent);
        } catch(ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}