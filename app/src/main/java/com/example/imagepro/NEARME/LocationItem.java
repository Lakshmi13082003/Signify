package com.example.imagepro.NEARME;

public class LocationItem {
    private final String name;
    private final String query;

    public LocationItem(String name, String query) {
        this.name = name;
        this.query = query;
    }

    public String getName() {
        return name;
    }

    public String getQuery() {
        return query;
    }
}
