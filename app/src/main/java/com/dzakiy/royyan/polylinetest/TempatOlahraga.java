package com.dzakiy.royyan.polylinetest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Zack on 18/11/2017.
 */

public class TempatOlahraga {
    private String title;
    private LatLng latLng;
    private String type; // football, basketball, badminton, futsal, bowling
    private String snippet;

    public TempatOlahraga(String title, LatLng latLng, String type, String snippet) {
        this.title = title;
        this.latLng = latLng;
        this.type = type;
        this.snippet = snippet;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getTitle() {
        return title;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getType() {
        return type;
    }
}
