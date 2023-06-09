package com.example.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityHospitalLocationBinding;

public class HospitalLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityHospitalLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHospitalLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        LatLng markedLocation = getIntent().getExtras().getParcelable("Location");
        LatLng userLocation = new LatLng(markedLocation.latitude, markedLocation.longitude);
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Current User Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14.0f));
    }
}