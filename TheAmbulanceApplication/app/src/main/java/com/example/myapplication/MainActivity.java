package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    LatLng locationPassed;
    ArrayList<String> HospitalLists;
    String Location;
    ArrayAdapter arrayAdapter;
    ListView hospitalsAround;
    LocationManager locationManager;
    LocationListener locationListener;
    double rawLatitude, rawLongitude, latitude, longitude;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        hospitalsAround = findViewById(R.id.HospitalsAround);

        HospitalLists = new ArrayList<String>();

        HospitalLists.add("Parawati Hospital \n George Town, Prayagraj");
        HospitalLists.add("Prachi Hospital \n New ShantiPuram, Lucknow Road, Prayagraj");
        HospitalLists.add("Vedanta Hospital \n Civil Lines, Prayagraj");
        HospitalLists.add("Madan Mohan Malviya eye hospital \n Beli, Prayagraj");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,HospitalLists);

        //Getting the user Location through locationManager and LocationListner
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                rawLatitude = location.getLatitude();
                rawLongitude = location.getLongitude();


                latitude = BigDecimal.valueOf(rawLatitude).setScale(3, RoundingMode.HALF_UP).doubleValue();
                longitude = BigDecimal.valueOf(rawLongitude).setScale(3, RoundingMode.HALF_UP).doubleValue();

                locationPassed = new LatLng(rawLatitude, rawLongitude);

                String LocationInString = String.valueOf(latitude)+" "+String.valueOf(longitude);

                Log.i("The user Location is: ", LocationInString);
                HospitalLists.add("The current user location is: "+LocationInString);
                hospitalsAround.setAdapter(arrayAdapter);
                endGPS();
            }
        };


        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0,locationListener);
        }


        hospitalsAround.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == HospitalLists.size() - 1) {
                    Intent intent = new Intent(getApplicationContext(), HospitalLocation.class);
                    intent.putExtra("Location", locationPassed);
                    startActivity(intent);
                }
            }
        });

    }

    //The function will end the continues updation of the user Location.
    public void endGPS(){
        try {
            locationManager.removeUpdates(locationListener);
            locationManager=null;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}