package com.example.mdbfocus;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView listOfLocations;
    LocationAdapter adapter;
    ArrayList<Location> locations;
    SearchView search;


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
