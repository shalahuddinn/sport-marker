package com.dzakiy.royyan.polylinetest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mAngkotStart, mAngkotEnd;
    private Bitmap mAngkotStart_pic, mAngkotEnd_pic;
    private URL mAngkotStart_url, mAngkotEnd_url;
    private String meansStart,meansEnd,angkotStart,angkotEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void keMain(View view) {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        setContentView(R.layout.activity_main);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng bandung = new LatLng(-6.88221,107.62752);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bandung));
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                bandung, 15);
        mMap.animateCamera(location);

        // dapat dari API Kiri
        meansStart = "angkot";
        meansEnd = "angkot";
        angkotStart = "dagocaringin";
        angkotEnd = "panghegardipatiukur";

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    mAngkotStart_url = new URL("https://kiri.travel/images/means/" + meansStart + "/baloon/" + angkotStart +".png");
                    mAngkotEnd_url = new URL("https://kiri.travel/images/means/" + meansEnd + "/baloon/" + angkotEnd +".png");
                    mAngkotStart_pic = BitmapFactory.decodeStream(mAngkotStart_url.openConnection().getInputStream());
                    mAngkotEnd_pic = BitmapFactory.decodeStream(mAngkotEnd_url.openConnection().getInputStream());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAngkotStart = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-6.88221,107.62752))
                                .title("Dago-Caringin")
                                .snippet("Population: 4,627,300")
                                .icon(BitmapDescriptorFactory.fromBitmap(mAngkotStart_pic))
//                                .icon(BitmapDescriptorFactory.fromResource(R.id.large_icon_uri))
//                                .icon(BitmapDescriptorFactory.fromFile("app\\src\\main\\res\\drawable\\logo\\Basketball_000000.png"))
//                                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("image_name",100,100)))
                                .infoWindowAnchor(0.5f, 0.5f));

                        mAngkotEnd = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-6.89899,107.60927))
                                .title("Panghegar-Dipatiukur")
                                .snippet("Population: 4,627,300")
                                 .icon(BitmapDescriptorFactory.fromBitmap(mAngkotEnd_pic))
                                .infoWindowAnchor(0.5f, 0.5f));
                    }
                });
            }
        });
        thread.start();
    }

    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

}
