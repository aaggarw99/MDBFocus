package com.example.mdbfocus;

import com.google.android.gms.location.Geofence;

import java.util.List;

public class GeofenceHelper {

    private List<Geofence> geofences;

    public GeofenceHelper(List<Geofence> geofences) {
        this.geofences = geofences;
    }
}
