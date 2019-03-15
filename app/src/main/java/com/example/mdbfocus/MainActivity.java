package com.example.mdbfocus;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
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
    SearchView search;

    private GeofencingClient geofencingClient;
    private List<Geofence> geofenceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locations = new ArrayList<>();
        // test data for now
        locations.add(new Location("Moffitt", 100, 100));

        listOfLocations = findViewById(R.id.rv);
        listOfLocations.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LocationAdapter(this, locations);
        listOfLocations.setAdapter(adapter);


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("SUCCESS!!!!", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("ERROR U IDIOT", "An error occurred: " + status);
            }
        });

//        geofencingClient = LocationServices.getGeofencingClient(this);
//
//        geofenceList = new ArrayList<>();
//
//        geofenceList.add(new Geofence.Builder()
//                // Set the request ID of the geofence. This is a string to identify this
//                // geofence.
//                .setRequestId(entry.getKey())
//
//                .setCircularRegion(
//                        entry.getValue().latitude,
//                        entry.getValue().longitude,
//                        50
//                )
//                .setExpirationDuration(5000)
//                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
//                        Geofence.GEOFENCE_TRANSITION_EXIT)
//                .build());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);



        search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setQueryHint("Enter description here");

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s = s.toLowerCase();
                List<Location> filteredList = new ArrayList<>();
                for (Location l : locations) {
                    String locationName = l.getName().toLowerCase();
                    if (locationName.contains(s)) {
                        filteredList.add(l);
                    }
                }
                adapter.setSearchOperation(filteredList);

                return false;
            }
        });

        return true;
    }
}
