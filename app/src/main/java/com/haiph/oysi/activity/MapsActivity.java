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
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    boolean showbutton;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";
    FloatingActionButton sweep, fab;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    LocationManager mLocationManager;
    LocationRequest mLocationRequest;
    com.google.android.gms.location.LocationListener listener;
    long UPDATE_INTERVAL = 3000;
    long FASTED_INTERVAL = 60000;
    LocationManager locationManager;
    LatLng latLng;
    boolean isPermission;
    public static final int MY_REQUEST_LOCATION = 1;
    Animation closeRotate, openRotate;
    SearchView searchMap;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    double latCauGiay,longCaugiay;

    int aqiCauGiay, nhietdoCauGiay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        closeRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.close_rotate);
        openRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.open_rotate);
        searchMap = findViewById(R.id.searchMap);
        sweep = findViewById(R.id.sweep);
        fab = findViewById(R.id.fabImage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        searchMap.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchMap.getQuery().toString();
                List<Address> addressList = new ArrayList<>();
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        addressList.clear();
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                        Address address = addressList.get(0);
                        Log.e("addressText",address.toString());
                        double latitude = address.getLatitude();
                        double longitude = address.getLongitude();
                        LatLng latLngAddress = new LatLng(latitude,longitude);
                        mMap.addMarker(new MarkerOptions().position(latLngAddress).title("location"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngAddress,12));
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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


    private void fabShow() {
        sweep.show();
    }

    private void fabHide() {
        sweep.hide();
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
        mMap = googleMap;


        if (latLng != null) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Current Location"))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.human));
            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(latLng).zoom(14)
                    .bearing(98).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
            mMap.addCircle(new CircleOptions().center(latLng).radius(1000).strokeColor(Color.BLACK).strokeWidth(2));
            getQuanCauGiay();


            sweep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MapsActivity.this, SweepAirQuality.class);
                    startActivity(i);
                }

            });
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

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
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

    public void getQuanCauGiay(){
        RetrofitService.getInstance().getLittleCity("Cau Giay","Hanoi","Vietnam",key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()){
                    List<Double> array = response.body().data.getLocation().getCoordinates();
                    latCauGiay = array.get(0);
                    longCaugiay= array.get(1);
                    nhietdoCauGiay=response.body().data.getCurrent().getWeather().getTp();
                    aqiCauGiay= response.body().data.getCurrent().getPollution().getAqius();
                    LatLng latLngCauGiay = new LatLng(latCauGiay,longCaugiay );
                    Log.e("caugiay", latLngCauGiay+"");
                    mMap.addMarker(new MarkerOptions().position(latLngCauGiay).title("Cầu giấy"));
                    CircleOptions CauGiayOption = new CircleOptions();
                    CauGiayOption.center(latLngCauGiay);
                    CauGiayOption.radius(1200);
                    CauGiayOption.strokeWidth(2).strokeColor(Color.BLACK);
                    Log.e("blabla",nhietdoCauGiay +" " +aqiCauGiay);
                    if (aqiCauGiay<50){
                        CauGiayOption.fillColor(Color.parseColor("#a8e05f"));
                    }else if (aqiCauGiay>=50 && aqiCauGiay<100){
                        CauGiayOption.fillColor(Color.parseColor("#fdd74b"));

                    }else if (aqiCauGiay>=100 &&aqiCauGiay<150){
                        CauGiayOption.fillColor(Color.parseColor("#fe9b57"));

                    }else  if (aqiCauGiay>=150 && aqiCauGiay<200){
                        CauGiayOption.fillColor(Color.parseColor("#fe6a69"));

                    }else if (aqiCauGiay>200){
                        CauGiayOption.fillColor(Color.parseColor("#940045"));
                    }
                }
            }
            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }

    public void getHanoi(){
        RetrofitService.getInstance().getLittleCity("Hanoi","Hanoi","Vietnam",key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {

            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }
    public void getQuocOai(){
        RetrofitService.getInstance().getLittleCity("Quoc Oai","Hanoi","Vietnam",key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }
    public void getSocSon(){
        RetrofitService.getInstance().getLittleCity("Soc Son","Hanoi","Vietnam",key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }
    public void getTayHo(){
        RetrofitService.getInstance().getLittleCity("Tay Ho","Hanoi","Vietnam",key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }
    public void getThachThat(){
        RetrofitService.getInstance().getLittleCity("Thach That","Hanoi","Vietnam",key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }
    public void getTrauQuy(){
        RetrofitService.getInstance().getLittleCity("Trau Quy","Hanoi","Vietnam","key").enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }
}
