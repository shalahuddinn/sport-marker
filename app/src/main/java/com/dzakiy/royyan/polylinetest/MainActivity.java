package com.dzakiy.royyan.polylinetest;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private int resID;
    private ImageView img;
    private Marker marker;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
    }

    public void keMaps(View view) {
        Intent i = new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(i);
        setContentView(R.layout.activity_maps);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap _googleMap) {

        mMap = _googleMap;
        // Set a listener for info window events.

        mapFragment.getMapAsync(this);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.custom_info_window_layout, null);

                TextView titleView = (TextView) v.findViewById(R.id.title);
                titleView.setText(marker.getTitle());

                TextView snippetView = (TextView) v.findViewById(R.id.snippet);
                snippetView.setText(marker.getSnippet());

                TextView latLngView = (TextView) v.findViewById(R.id.latlng);
                latLngView.setText(marker.getPosition().toString());

                // Returning the view containing InfoWindow contents
                return v;
            }
        });
        googleMapSetup();

    }

    public void googleMapSetup() {
        LatLng bandung = new LatLng(-33.852, 151.211);
        mMap.addMarker(new MarkerOptions()
                .position(bandung)
                .title("Marker in bandung"));

        List<TempatOlahraga> tempatOlahragaList = null;
        // (1) isi tempatOlahragaList
            // later: connect to firebase
        TempatOlahraga a = new TempatOlahraga("Tennis oke", new LatLng(-6.893227, 107.610458), "tennis", "Tempat main tennis paling oke di ITB");
        TempatOlahraga b = new TempatOlahraga("Bola mantap", new LatLng(-6.892002, 107.610404), "futsal", "Futsal keren coy");
        TempatOlahraga c = new TempatOlahraga("Bowling mowling", new LatLng(-6.895048, 107.611295), "bowling", "Bowling paling asik di dunia");
        TempatOlahraga d = new TempatOlahraga("Billiard sedap", new LatLng(-6.891927, 107.608967), "billiard", "Butuh nongkrong? bukan disini pastinya.");

        tempatOlahragaList.add(a);
        tempatOlahragaList.add(b);
        tempatOlahragaList.add(c);
        tempatOlahragaList.add(d);

        marker = mMap.addMarker(new MarkerOptions()
                        .position(a.getLatLng())
                        .title(a.getTitle())
                        .snippet(a.getSnippet()));

        // (2) assign tempatOlahraga ke marker > addMarker
        for (int i=0; i<tempatOlahragaList.size(); i++) {
            resID = getResources().getIdentifier(tempatOlahragaList.get(i).getType() , "drawable", getPackageName());
            // img = (ImageView) findViewById(resID);
            marker = mMap.addMarker(new MarkerOptions()
                            .position(tempatOlahragaList.get(i).getLatLng())
                            .title(tempatOlahragaList.get(i).getTitle())
                            .snippet(tempatOlahragaList.get(i).getSnippet())
            //        .icon(BitmapDescriptorFactory.fromBitmap(img))
            //        .icon(BitmapDescriptorFactory.fromAsset("Basketball_000000.png"))
            );
        }

        LatLng defPos = new LatLng(-6.891927, 107.608967
        );
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(a.getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(defPos.latitude, defPos.longitude), 12.0f));

        // (3) buat custom infowindow


    }

    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, marker.getTitle() + " window clicked",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
