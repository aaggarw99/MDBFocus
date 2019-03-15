package com.example.mdbfocus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView listOfLocations;
    LocationAdapter adapter;
    ArrayList<Location> locations;
    DatabaseHelper mDatabaseHelper;


    private GeofencingClient geofencingClient;
    private List<Geofence> geofenceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHelper = new DatabaseHelper(this);

        // gets stored data in database
        locations = mDatabaseHelper.getAllData();

        listOfLocations = findViewById(R.id.rv);
        listOfLocations.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LocationAdapter(this, locations);
        listOfLocations.setAdapter(adapter);


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        Places.initialize(this, Environment.API_KEY);

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng latLng = place.getLatLng();
                double lat, lon;
                if (latLng == null) {
                    lat = 0;
                    lon = 0;
                } else {
                    lat = latLng.latitude;
                    lon = latLng.longitude;
                }
                Log.i("Location info", "Name: " + place.getName() + "; Address: " + place.getAddress()
                + "; Lat: " + lat + "; Lon: " + lon);
                Location l = new Location(place.getName(), place.getAddress(), lat, lon);
                locations.add(l);
                addLocationToDB(l);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("ERROR U IDIOT", "An error occurred: " + status);
            }
        });

    }

    private void addLocationToDB(Location l) {
        boolean success = mDatabaseHelper.addData(l);
        if (success) {
            Toast.makeText(this, "Successfully added the location", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error in adding the location", Toast.LENGTH_LONG).show();
        }
    }
}
