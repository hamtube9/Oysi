package com.haiph.oysi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.haiph.oysi.R;
import com.haiph.oysi.response.CurrentLocationRespone;
import com.haiph.oysi.service.RetrofitService;
import com.haiph.oysi.service.ScreenShot;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import me.itangqi.waveloadingview.WaveLoadingView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SweepAirQuality extends AppCompatActivity {
    WaveLoadingView mWaveLoadingView;
    SharedPreferences sharedPreferences;
    FloatingActionButton fabScreenshot;
    View main;
    ImageView imgScreenshot;
    TextView tvGetKhongKhi,tvGetNhietDo,tvAQI;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweep_air_quality);
        tvAQI=findViewById(R.id.tvAQI);
        main=findViewById(R.id.main);
        imgScreenshot=findViewById(R.id.imgScreenshot);
        fabScreenshot=findViewById(R.id.fabScreenshot);
        fabScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = ScreenShot.takescreenshotOfRootView(imgScreenshot);
                imgScreenshot.setImageBitmap(b);
                storeScreenshot(b, String.valueOf(imgScreenshot));
            }
        });
        imgScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getShare();
            }
        });
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
                }


                    if (Integer.parseInt(tvGetKhongKhi.getText().toString())>=1 && Integer.parseInt(tvGetKhongKhi.getText().toString())<=50){
                    mWaveLoadingView.setProgressValue(Integer.parseInt(tvGetKhongKhi.getText().toString()));
                    mWaveLoadingView.setAnimDuration(3000);
                    mWaveLoadingView.setWaveColor(Color.GREEN);
                    mWaveLoadingView.setBorderColor(Color.GREEN);
                    mWaveLoadingView.setCenterTitle("Không khí trong lành");

                    mWaveLoadingView.startAnimation();

                }else if (Integer.parseInt(tvGetKhongKhi.getText().toString())>=51 && Integer.parseInt(tvGetKhongKhi.getText().toString())<=100) {
                    mWaveLoadingView.setProgressValue(Integer.parseInt(tvGetKhongKhi.getText().toString()));
                    mWaveLoadingView.setAnimDuration(5000);
                    mWaveLoadingView.setWaveColor(Color.YELLOW);
                    mWaveLoadingView.setBorderColor(Color.YELLOW);
                    mWaveLoadingView.setCenterTitle("Không khí có chút bụi bẩn");
                    mWaveLoadingView.setCenterTitleColor(Color.GRAY);
                    mWaveLoadingView.startAnimation();
                }else if (Integer.parseInt(tvGetKhongKhi.getText().toString())>=101 && Integer.parseInt(tvGetKhongKhi.getText().toString())<=150 ) {
                    mWaveLoadingView.setProgressValue(Integer.parseInt(tvGetKhongKhi.getText().toString()));
                    mWaveLoadingView.setAnimDuration(7000);
                    mWaveLoadingView.setWaveColor(Color.RED);
                    mWaveLoadingView.setBorderColor(Color.RED);
                    mWaveLoadingView.setCenterTitle("Có sự ô nhiễm trong không khí");
                    mWaveLoadingView.startAnimation();
                }else if (Integer.parseInt(tvGetKhongKhi.getText().toString())>=151 && Integer.parseInt(tvGetKhongKhi.getText().toString())<=200 ) {
                    mWaveLoadingView.setProgressValue(Integer.parseInt(tvGetKhongKhi.getText().toString()));
                    mWaveLoadingView.setAnimDuration(9000);
                    mWaveLoadingView.setWaveColor(Color.DKGRAY);
                    mWaveLoadingView.setBorderColor(Color.DKGRAY);
                    mWaveLoadingView.setCenterTitle("Không khí ô nhiễm nặng nề ");
                    mWaveLoadingView.startAnimation();
                }else if (Integer.parseInt(tvGetKhongKhi.getText().toString())>=201  ) {
                    mWaveLoadingView.setProgressValue(Integer.parseInt(tvGetKhongKhi.getText().toString()));
                    mWaveLoadingView.setAnimDuration(10000);
                    mWaveLoadingView.setWaveColor(Color.GRAY);
                    mWaveLoadingView.setBorderColor(Color.GRAY);
                    mWaveLoadingView.setCenterTitle("Ô nhiễm không khí nghiêm trọng ");
                    mWaveLoadingView.startAnimation();
                }

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
                        tvGetNhietDo.setText(" "+response.body().data.getCurrent().getWeather().getTp()+"°C");

                        tvGetKhongKhi.setText(""+response.body().data.getCurrent().getPollution().getAqius());
                    Log.e("dataNearest_city", String.valueOf(response.body().data.getCurrent()));
                }
            }

            @Override
            public void onFailure(Call<CurrentLocationRespone> call, Throwable t) {

            }
        });
    }
    private void getShare() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Uri bmpUri = (Uri) getLocalImageBitmapUri(imgScreenshot);
        if (bmpUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri getLocalImageBitmapUri(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
    public void storeScreenshot(Bitmap bitmap, String filename) {
        String path = Environment.getExternalStorageDirectory().toString() + "/" + filename;
        OutputStream out = null;
        File imageFile = new File(path);

        try {
            out = new FileOutputStream(imageFile);
            // choose JPEG format
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
        } catch (FileNotFoundException e) {
            // manage exception ...
        } catch (IOException e) {
            // manage exception ...
        } finally {

            try {
                if (out != null) {
                    out.close();
                }

            } catch (Exception exc) {
            }

        }
    }

}
