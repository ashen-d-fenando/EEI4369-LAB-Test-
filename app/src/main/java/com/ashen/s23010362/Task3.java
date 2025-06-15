package com.ashen.s23010362;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Task3 extends AppCompatActivity implements OnMapReadyCallback {



    private GoogleMap myMap;
    private EditText addressInput;
    private Button btnShowLocation;

    private Button sensorPage_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        addressInput = findViewById(R.id.address_input);
        btnShowLocation = findViewById(R.id.btnShowLocation);


        // Initialize the map here if needed
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationOnMap();
            }
        });

        sensorPage_button = findViewById(R.id.sensorPage_button);
        sensorPage_button.setOnClickListener(v -> {
            // Corrected the class name to Task04
            Intent intent = new Intent(Task3.this, Task04.class);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

       myMap = googleMap;
        LatLng colombo = new LatLng(6.9271, 79.8612); // default location (Colombo, Sri Lanka)
        myMap.addMarker(new MarkerOptions().position(colombo).title("Marker in Colombo"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colombo, 12));

    }
    private void showLocationOnMap() {

        // Get and Store User entered location
        String location = addressInput.getText().toString().trim();

        // Validate the input
        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter an address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use Geocoder to convert the address to latitude and longitude
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());


        try {
            List<Address> addressList = geocoder.getFromLocationName(location, 1);

            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                myMap.clear();
                myMap.addMarker(new MarkerOptions().position(latLng).title(location));
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
            } else {
                Toast.makeText(this, "Address not found.", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Service not available.", Toast.LENGTH_LONG).show();
        }
    }





}