package com.haiph.oysi.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.android.libraries.places.api.Places;
//import com.google.android.libraries.places.api.model.AutocompletePrediction;
//import com.google.android.libraries.places.api.model.Place;
//import com.google.android.libraries.places.api.net.PlacesClient;
//import com.google.android.libraries.places.widget.Autocomplete;
//import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
//import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.haiph.oysi.R;
import com.haiph.oysi.response.LittleCityResponse;
import com.haiph.oysi.service.RetrofitService;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, View.OnClickListener {
    boolean showbutton;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";
    FloatingActionButton sweep, fab,fabthaydoimap;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    LocationManager mLocationManager;
    LocationRequest mLocationRequest;
    long UPDATE_INTERVAL = 3000;
    long FASTED_INTERVAL = 60000;
    LocationManager locationManager;
    LatLng latLng;
    boolean isPermission;
    Animation closeRotate, openRotate;
    SearchView searchMap;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int tt=15;
    int t = 1;
  //  String apiPlacesKey = "AIzaSyAxwwrQsYolvnpLaN6jDy__fwAtFeqgOwY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initView();

        if (requestSinglePermission()) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            checkLocation();
        }
    }

    private void initView() {
        closeRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.close_rotate);
        openRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.open_rotate);
       searchMap = findViewById(R.id.searchMap);
        sweep = findViewById(R.id.sweep);
        fab = findViewById(R.id.fabImage);
        fab.setOnClickListener(this);
        fabthaydoimap = findViewById(R.id.fabthaydoimap);
        fabthaydoimap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map();
            }
        });
        
        searchMap.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                    String g = String.valueOf(searchMap.getQuery());

                    Geocoder geocoder = new Geocoder(getBaseContext());
                    List<Address> addresses = null;

                    try {
                        addresses = geocoder.getFromLocationName(g, 3);
                        if (addresses != null && !addresses.equals(""))
                            search(addresses);

                    } catch (Exception e) {

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    protected void search(List<Address> addresses) {
        Address address = addresses.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(address.getAddressLine(0));
        tt=15;
        mMap.clear();
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(tt));
    }

    public void map(){
        t+=1;
        mMap.setMapType(t);
        if(t==4){
            t=0;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sweep:
                Intent i = new Intent(MapsActivity.this, SweepAirQuality.class);
                startActivity(i);
                break;
            case R.id.fabImage:
                if (showbutton == false) {
                    openRotate.setDuration(100);
                    fab.startAnimation(openRotate);
                    fabShow();

                    showbutton = true;
                } else {
                    closeRotate.setDuration(100);
                    fab.startAnimation(closeRotate);
                    fabHide();
                    showbutton = false;
                }
                break;
        }
    }


    private void fabShow() {
        fabthaydoimap.show();
        sweep.show();
    }

    private void fabHide() {
        sweep.hide();
        fabthaydoimap.hide();
    }

    private boolean checkLocation() {
        if (!isLocationEnabled()) {
            showAlert();
        }
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enabled Connection").setMessage("Xin hãy mở vị trí để sử dụng app");
        dialog.setPositiveButton("Setting Location ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent myItent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(myItent);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean requestSinglePermission() {

        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).
                withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            isPermission = false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
        return isPermission;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getApplicationContext());
        mMap = googleMap;
        if (latLng != null) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Current Location"))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.human));
            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(latLng).zoom(10)
                    .bearing(98).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
            mMap.addCircle(new CircleOptions().center(latLng).radius(1000).strokeColor(Color.BLACK).strokeWidth(2));
            sweep.setOnClickListener(this);
        }


    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startLocationUpdate();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation == null) {
            startLocationUpdate();

        } else {
            Toast.makeText(this, "Đang lấy vị trí hiện tại", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLocationUpdate() {
        mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL).setFastestInterval(FASTED_INTERVAL);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onLocationChanged(locationResult.getLastLocation());
            }
        }, Looper.myLooper());
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(final Location location) {
        String mess = "Cập nhật Vị trí" + Double.toString(location.getLatitude()) + " " + Double.toString(location.getLongitude());
        sharedPreferences = getSharedPreferences("Location", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("Longitude", String.valueOf(location.getLongitude()));
        editor.putString("Latitude", String.valueOf(location.getLatitude()));
        editor.commit();
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public void getQuanCauGiay() {
        RetrofitService.getInstance().getLittleCity("Cau Giay", "Hanoi", "Vietnam", key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()) {
                    List<Double> array = response.body().data.getLocation().getCoordinates();
                    double latCauGiay = array.get(0);
                    double longCaugiay = array.get(1);
                    int nhietdoCauGiay = response.body().data.getCurrent().getWeather().getTp();
                    int aqiCauGiay = response.body().data.getCurrent().getPollution().getAqius();

                    SharedPreferences sharedPreferences = getSharedPreferences("latlongCauGiay", MODE_PRIVATE);
                    SharedPreferences.Editor editCauGiay = sharedPreferences.edit();
                    editCauGiay.putString("latCauGiay", String.valueOf(latCauGiay));
                    editCauGiay.putString("longCauGiay", String.valueOf(longCaugiay));
                    editCauGiay.putString("aqiCauGiay", String.valueOf(aqiCauGiay));
                    editCauGiay.commit();

//                    SharedPreferences sharedPreferences = getSharedPreferences("latlongCauGiay",MODE_PRIVATE);
//                    String latCauGiay =sharedPreferences.getString("latCauGiay","");
//                    String longCauGiay = sharedPreferences.getString("longCauGiay","");
//                    int aqiCauGiay = Integer.parseInt(sharedPreferences.getString("aqiCauGiay",""));
//                    Log.e("latlng",latCauGiay + " "+longCauGiay+ " "+ aqiCauGiay);
//                    LatLng latLngCauGiay = new LatLng(Double.parseDouble(latCauGiay),Double.parseDouble(longCauGiay));
//                    Log.e("caugiay", latLngCauGiay+"");
//                    mMap.addMarker(new MarkerOptions().position(latLngCauGiay).title("Cầu giấy"));
//                    CircleOptions CauGiayOption = new CircleOptions();
//                    mMap.addCircle(CauGiayOption.center(latLngCauGiay).radius(1000).strokeColor(Color.BLACK).strokeWidth(2));
//                    if (aqiCauGiay<50){
//                        CauGiayOption.fillColor(Color.parseColor("#a8e05f"));
//                    }else if (aqiCauGiay>=50 && aqiCauGiay<100){
//                        CauGiayOption.fillColor(Color.parseColor("#fdd74b"));
//
//                    }else if (aqiCauGiay>=100 &&aqiCauGiay<150){
//                        CauGiayOption.fillColor(Color.parseColor("#fe9b57"));
//
//                    }else  if (aqiCauGiay>=150 && aqiCauGiay<200){
//                        CauGiayOption.fillColor(Color.parseColor("#fe6a69"));
//
//                    }else if (aqiCauGiay>200){
//                        CauGiayOption.fillColor(Color.parseColor("#940045"));
//                    }
//
                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }

}
