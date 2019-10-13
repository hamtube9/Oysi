package com.haiph.oysi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.haiph.oysi.R;
import com.haiph.oysi.model.location.CurrentLocation;
import com.haiph.oysi.response.CurrentLocationRespone;
import com.haiph.oysi.service.RetrofitService;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.itangqi.waveloadingview.WaveLoadingView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SweepAirQuality extends AppCompatActivity {
    WaveLoadingView mWaveLoadingView;
    SharedPreferences sharedPreferences;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";
    List<CurrentLocation> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweep_air_quality);
        mWaveLoadingView = findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setAnimDuration(5000);
        mWaveLoadingView.startAnimation();




    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mWaveLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mWaveLoadingView.setProgressValue(18);
                mWaveLoadingView.setAnimDuration(8000);
                mWaveLoadingView.startAnimation();
//                mWaveLoadingView.setProgressValue(50);
//                mWaveLoadingView.setBorderWidth(10);
//                mWaveLoadingView.setAmplitudeRatio(60);
//                mWaveLoadingView.setWaveColor(Color.GRAY);
//                mWaveLoadingView.setBorderColor(Color.GRAY);
//        mWaveLoadingView.pauseAnimation();
//        mWaveLoadingView.resumeAnimation();
//        mWaveLoadingView.cancelAnimation();
            }
        });

        getDataCurrentLocation();
    }

    private void getDataCurrentLocation() {
        sharedPreferences = getSharedPreferences("Location", Context.MODE_PRIVATE);
        String Longitude = sharedPreferences.getString("Longitude", "");
        String Latitude = sharedPreferences.getString("Latitude", "");

        Log.e("locationCurrent", "" + Longitude + " | " + Latitude);
        RetrofitService.getInstance().getCurrentLocation(Latitude,Longitude,key).enqueue(new Callback<CurrentLocationRespone>() {
            @Override
            public void onResponse(Call<CurrentLocationRespone> call, Response<CurrentLocationRespone> response) {
                if (response.isSuccessful()){
                    Log.e("dataNearest_city", String.valueOf(response.body().data));
                }
            }

            @Override
            public void onFailure(Call<CurrentLocationRespone> call, Throwable t) {

            }
        });
    }
}
