package com.example.mdbfocus;

public class Location {

    public String name;
    public int lat;
    public int lon;

    public Location(String name, int lat, int lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }
}
