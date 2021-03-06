package com.example.garbagecollector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.garbagecollector.fragments.AddPlaceFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapMarkActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    FragmentTransaction transaction;
    AddPlaceFragment addPlaceFragment;
    private boolean isClicked = false;
    Geocoder geocoder;
    List<Address> addresses;
    Locale ruLoc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_mark);

        ruLoc = new Locale("ru");
       // geocoder = new Geocoder(this, Locale.getDefault());
        geocoder = new Geocoder(this, ruLoc);
        searchView = findViewById(R.id.sv_map_mark);
        addPlaceFragment = new AddPlaceFragment();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_mark);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(MapMarkActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;

            }
        });




        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                isClicked = !isClicked;
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String address = addresses.get(0).getAddressLine(0);
                //String city = addresses.get(0).getLocality();
                //String state = addresses.get(0).getAdminArea();
                //String country = addresses.get(0).getCountryName();
                //String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                if (isClicked) {
                    Bundle b = new Bundle();
                    b.putString("adress", address);
                    map.addMarker(new MarkerOptions().position(latLng).title(address));
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.ll_add, addPlaceFragment);
                    transaction.commit();
                    addPlaceFragment.setArguments(b);

                } else{
                    map.clear();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.remove(addPlaceFragment);
                    transaction.commit();
                }

            }
        });
        LatLng stavropol = new LatLng(45.0354, 41.96);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(stavropol, 10));


    }
}