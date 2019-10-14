package com.haiph.oysi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.haiph.oysi.R;
import com.haiph.oysi.response.CurrentLocationRespone;
import com.haiph.oysi.service.RetrofitService;


import me.itangqi.waveloadingview.WaveLoadingView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SweepAirQuality extends AppCompatActivity {
    WaveLoadingView mWaveLoadingView;
    SharedPreferences sharedPreferences;
    TextView tvGetKhongKhi,tvGetNhietDo,tvAQI,tvTip;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";
//    List<CurrentLocation> list;
//    RecyclerView rcCurrentLocation;
//    AdapterCurrentLocation adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweep_air_quality);
        tvTip=findViewById(R.id.tvTip);
        tvAQI=findViewById(R.id.tvAQI);
        mWaveLoadingView = findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setAnimDuration(5000);
        mWaveLoadingView.startAnimation();
        tvGetKhongKhi=findViewById(R.id.tvGetKhongKhi);
        tvGetNhietDo=findViewById(R.id.tvGetNhietDo);


        getDataCurrentLocation();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getDataCurrentLocation();
        mWaveLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvGetKhongKhi.getVisibility()== View.INVISIBLE && tvGetNhietDo.getVisibility()==View.INVISIBLE){
                    tvGetKhongKhi.setVisibility(View.VISIBLE);
                    tvGetNhietDo.setVisibility(View.VISIBLE);
                    tvAQI.setVisibility(View.VISIBLE);
                    tvTip.setVisibility(View.VISIBLE);
                }


                    if (Integer.parseInt(tvGetKhongKhi.getText().toString())>=1 && Integer.parseInt(tvGetKhongKhi.getText().toString())<=50){
                    mWaveLoadingView.setProgressValue(Integer.parseInt(tvGetKhongKhi.getText().toString()));
                    mWaveLoadingView.setAnimDuration(3000);
                    mWaveLoadingView.setWaveColor(Color.GREEN);
                    mWaveLoadingView.setBorderColor(Color.GREEN);
                        tvTip.setText("Tip : Không khí trong lành, hãy ra ngoài tận hưởng ");
                    mWaveLoadingView.setCenterTitle(Integer.parseInt(tvGetKhongKhi.getText().toString())+"");

                    mWaveLoadingView.startAnimation();

                }else if (Integer.parseInt(tvGetKhongKhi.getText().toString())>=51 && Integer.parseInt(tvGetKhongKhi.getText().toString())<=100) {
                    mWaveLoadingView.setProgressValue(Integer.parseInt(tvGetKhongKhi.getText().toString()));
                    mWaveLoadingView.setAnimDuration(5000);
                    mWaveLoadingView.setWaveColor(Color.YELLOW);
                    mWaveLoadingView.setBorderColor(Color.YELLOW);
                        tvTip.setText("Tip : Không khí ổn định, có chút bụi");

                        mWaveLoadingView.setCenterTitle(Integer.parseInt(tvGetKhongKhi.getText().toString())+"");
                    mWaveLoadingView.setCenterTitleColor(Color.GRAY);
                    mWaveLoadingView.startAnimation();
                }else if (Integer.parseInt(tvGetKhongKhi.getText().toString())>=101 && Integer.parseInt(tvGetKhongKhi.getText().toString())<=150 ) {
                    mWaveLoadingView.setProgressValue(Integer.parseInt(tvGetKhongKhi.getText().toString()));
                    mWaveLoadingView.setAnimDuration(7000);
                    mWaveLoadingView.setWaveColor(Color.RED);
                    mWaveLoadingView.setBorderColor(Color.RED);
                        tvTip.setTextColor(getResources().getColor(R.color.alert));
                        tvTip.setText("Tip : Đang có sự ô nhiễm trong không khí, ra đường nên mang khẩu trang");

                        mWaveLoadingView.setCenterTitle(Integer.parseInt(tvGetKhongKhi.getText().toString())+"");
                    mWaveLoadingView.setCenterTitleSize(18);
                    mWaveLoadingView.startAnimation();
                }else if (Integer.parseInt(tvGetKhongKhi.getText().toString())>=151 && Integer.parseInt(tvGetKhongKhi.getText().toString())<=200 ) {
                    mWaveLoadingView.setProgressValue(Integer.parseInt(tvGetKhongKhi.getText().toString()));
                    mWaveLoadingView.setAnimDuration(9000);
                    mWaveLoadingView.setWaveColor(Color.DKGRAY);
                    mWaveLoadingView.setBorderColor(Color.DKGRAY);
                        tvTip.setTextColor(getResources().getColor(R.color.important));
                        tvTip.setText("Tip : Không khí đầy khói bụi, cẩn thận khi ra đường");

                        mWaveLoadingView.setCenterTitle(Integer.parseInt(tvGetKhongKhi.getText().toString())+"");
                    mWaveLoadingView.startAnimation();
                }else if (Integer.parseInt(tvGetKhongKhi.getText().toString())>=201  ) {
                    mWaveLoadingView.setProgressValue(Integer.parseInt(tvGetKhongKhi.getText().toString()));
                    mWaveLoadingView.setAnimDuration(10000);
                    mWaveLoadingView.setWaveColor(Color.GRAY);
                    mWaveLoadingView.setBorderColor(Color.GRAY);
                        tvTip.setTextColor(getResources().getColor(R.color.important));
                        tvTip.setText("Tip : KHÔNG KHÍ Ô NHIỄM TRẦM TRỌNG");

                        mWaveLoadingView.setCenterTitle(Integer.parseInt(tvGetKhongKhi.getText().toString())+"");
                    mWaveLoadingView.startAnimation();
                }
//
//                mWaveLoadingView.setProgressValue(231);
//                mWaveLoadingView.setBorderWidth(10);
//                mWaveLoadingView.setAmplitudeRatio(60);
//                mWaveLoadingView.setWaveColor(Color.GRAY);
//                mWaveLoadingView.setBorderColor(Color.GRAY);
//                mWaveLoadingView.startAnimation();
//        mWaveLoadingView.pauseAnimation();
//        mWaveLoadingView.resumeAnimation();
//        mWaveLoadingView.cancelAnimation();
            }
        });

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
                        tvGetNhietDo.setText(response.body().data.getCurrent().getWeather().getTp()+"°C");

                        tvGetKhongKhi.setText(""+response.body().data.getCurrent().getPollution().getAqius());
                    Log.e("dataNearest_city", String.valueOf(response.body().data.getCurrent()));
                }
            }

            @Override
            public void onFailure(Call<CurrentLocationRespone> call, Throwable t) {

            }
        });
    }
}
