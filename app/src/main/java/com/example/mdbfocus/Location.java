package com.example.mdbfocus;

public class Location {

    public String name;
    public double lat;
    public double lon;
    public String address;

    public Location(String name, String address, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
