package com.haiph.oysi.fragment;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.haiph.oysi.R;
import com.haiph.oysi.response.LittleCityResponse;
import com.haiph.oysi.service.RetrofitService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragmentMyCountry extends Fragment implements OnMapReadyCallback {
    TextView tvHanoi, tvHCM;
    TextView tvAQIHanoi, tvNhietDoHanoi, tvAQIHCM, tvNhietDoHCM;
    TextView tvTipHanoi, tvTipHCM;
    ImageView imgThoiTietHanoi, imgEmotionHanoi;
    ImageView imgThoiTietHCM, imgEmotionHCM;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";
    int aqi, nhietdo;
    GoogleMap mMaps;
    MapView mapView;
    View views;
    LinearLayout bgAQIHCM, bgAQIHCM1, bgAQIHanoi, bgAQIHanoi1, bgImageHanoi, bgImageHCM;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_country, container, false);
        tvHanoi = view.findViewById(R.id.tvHanoi);
        tvAQIHanoi = view.findViewById(R.id.tvAQIHanoi);
        tvTipHanoi = view.findViewById(R.id.tvTipHanoi);
        imgEmotionHanoi = view.findViewById(R.id.imgEmotionHanoi);
        imgThoiTietHanoi = view.findViewById(R.id.imgThoiTietHanoi);
        tvNhietDoHanoi = view.findViewById(R.id.tvNhietDoHanoi);
        bgAQIHanoi = view.findViewById(R.id.bgAQIHanoi);
        bgAQIHanoi1 = view.findViewById(R.id.bgAQIHanoi1);
        bgImageHanoi = view.findViewById(R.id.bgImgHanoi);


        tvHCM = view.findViewById(R.id.tvHCM);
        tvAQIHCM = view.findViewById(R.id.tvAQIHCM);
        tvNhietDoHCM = view.findViewById(R.id.tvNhietDoHCM);
        imgThoiTietHCM = view.findViewById(R.id.imgThoiTietHCM);
        imgEmotionHCM = view.findViewById(R.id.imgEmotionHCM);
        bgAQIHCM = view.findViewById(R.id.bgAQIHCM);
        bgAQIHCM1 = view.findViewById(R.id.bgAQIHCM1);
        bgImageHCM = view.findViewById(R.id.bgImgHCM);


        mapView = view.findViewById(R.id.miniMap);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        getHanoi();
        getHCM();

        return view;
    }


    private void getHCM() {
        RetrofitService.getInstance().getLittleCity("Ho Chi Minh City", "Ho Chi Minh City", "Vietnam", key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Log.e("mycountry", response.body().data.getCity() + "");
                    tvHCM.setText(response.body().data.getCity());
                    int aqiHCM = response.body().data.getCurrent().getPollution().getAqius();
                    tvAQIHCM.setText(String.valueOf(aqiHCM));
                    //aqiHCM = response.body().data.getCurrent().getPollution().getAqius();

                    int nhietdoHCM = response.body().data.getCurrent().getWeather().getTp();
                    tvNhietDoHCM.setText(String.valueOf(nhietdoHCM));


                    if (aqiHCM > 0 && aqiHCM < 50) {
                        imgEmotionHCM.setImageResource(R.drawable.veryhappy);
//                        bgImageHCM.setBackgroundResource(R.color.goodH);
//                        bgAQIHCM.setBackgroundResource(R.color.good);
//                        bgAQIHCM1.setBackgroundResource(R.color.good);
                    } else if (aqiHCM > 50 && aqiHCM < 100) {
                        imgEmotionHCM.setImageResource(R.drawable.normal);
//                        bgImageHCM.setBackgroundResource(R.color.normalH);
//                        bgAQIHCM.setBackgroundResource(R.color.normal);
//                        bgAQIHCM1.setBackgroundResource(R.color.normal);
                    } else if (aqiHCM > 100 && aqiHCM < 150) {
                        imgEmotionHCM.setImageResource(R.drawable.sad);
//                        bgImageHCM.setBackgroundResource(R.color.notgoodH);
//                        bgAQIHCM.setBackgroundResource(R.color.notgood);
//                        bgAQIHCM1.setBackgroundResource(R.color.notgood);
                    } else if (aqiHCM > 150 && aqiHCM < 200) {
                        imgEmotionHCM.setImageResource(R.drawable.afraid);
//                        bgImageHCM.setBackgroundResource(R.color.pollutedH);
//                        bgAQIHCM.setBackgroundResource(R.color.polluted);
//                        bgAQIHCM1.setBackgroundResource(R.color.polluted);
                    } else if (aqiHCM > 200) {
                        imgEmotionHCM.setImageResource(R.drawable.fear);
//                        bgImageHCM.setBackgroundResource(R.color.soimportant);
//                        bgAQIHCM.setBackgroundResource(R.color.important);
//                        bgAQIHCM1.setBackgroundResource(R.color.important);
                    }

                    if (nhietdoHCM > 35) {
                        imgThoiTietHCM.setImageResource(R.drawable.sunhot);
                    } else if (nhietdoHCM >= 30 && nhietdoHCM < 35) {
                        imgThoiTietHCM.setImageResource(R.drawable.sunny);

                    } else if (nhietdoHCM < 30 && nhietdoHCM >= 25) {
                        imgThoiTietHCM.setImageResource(R.drawable.cloudy);

                    } else if (nhietdoHCM < 25 && nhietdoHCM >= 20) {
                        imgThoiTietHCM.setImageResource(R.drawable.clouds);

                    } else if (nhietdoHCM < 20 && nhietdoHCM >= 6) {
                        imgThoiTietHCM.setImageResource(R.drawable.windy);
                    } else if (nhietdoHCM < 6 && nhietdoHCM >= 0) {
                        imgThoiTietHCM.setImageResource(R.drawable.winter);
                    } else if (nhietdoHCM < 0) {
                        imgThoiTietHCM.setImageResource(R.drawable.snow);
                    }

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }

    private void getHanoi() {
        RetrofitService.getInstance().getLittleCity("Hanoi", "Hanoi", "Vietnam", key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Log.e("mycountry", response.body().data.getCity() + "");
                    tvHanoi.setText(response.body().data.getCity());
                    aqi = response.body().data.getCurrent().getPollution().getAqius();
                    Log.e("aqi", aqi + "");
                    tvAQIHanoi.setText(String.valueOf(aqi));
                    nhietdo = response.body().data.getCurrent().getWeather().getTp();
                    tvNhietDoHanoi.setText(String.valueOf(nhietdo));

                    if (aqi > 0 && aqi < 50) {
                        imgEmotionHanoi.setImageResource(R.drawable.veryhappy);
//                        bgImageHanoi.setBackgroundResource(R.color.normalH);
//                        bgAQIHanoi1.setBackgroundResource(R.color.normal);
//                        bgAQIHanoi.setBackgroundResource(R.color.normal);
                    } else if (aqi > 50 && aqi < 100) {
                        imgEmotionHanoi.setImageResource(R.drawable.normal);

//                        bgImageHanoi.setBackgroundResource(R.color.normalH);
//                        bgAQIHanoi1.setBackgroundResource(R.color.normal);
//                        bgAQIHanoi.setBackgroundResource(R.color.normal);
                    } else if (aqi > 100 && aqi < 150) {
                        imgEmotionHanoi.setImageResource(R.drawable.sad);
//                        bgImageHanoi.setBackgroundResource(R.color.notgoodH);
//                        bgAQIHanoi1.setBackgroundResource(R.color.notgood);
//                        bgAQIHanoi.setBackgroundResource(R.color.notgood);
                    } else if (aqi > 150 && aqi < 200) {
                        imgEmotionHanoi.setImageResource(R.drawable.afraid);
//                        bgImageHanoi.setBackgroundResource(R.color.pollutedH);
//                        bgAQIHanoi.setBackgroundResource(R.color.polluted);
//                        bgAQIHanoi1.setBackgroundResource(R.color.polluted);
                    } else if (aqi > 200) {
                        imgEmotionHanoi.setImageResource(R.drawable.fear);
//                        bgImageHanoi.setBackgroundResource(R.color.soimportant);
//                        bgAQIHanoi1.setBackgroundResource(R.color.important);
//                        bgAQIHanoi.setBackgroundResource(R.color.important);
                    }

                    if (nhietdo > 35) {
                        imgThoiTietHanoi.setImageResource(R.drawable.sunhot);
                    } else if (nhietdo >= 30 && nhietdo < 35) {
                        imgThoiTietHanoi.setImageResource(R.drawable.sunny);

                    } else if (nhietdo < 30 && nhietdo >= 25) {
                        imgThoiTietHanoi.setImageResource(R.drawable.cloudy);

                    } else if (nhietdo < 25 && nhietdo >= 20) {
                        imgThoiTietHanoi.setImageResource(R.drawable.clouds);

                    } else if (nhietdo < 20 && nhietdo >= 6) {
                        imgThoiTietHanoi.setImageResource(R.drawable.windy);
                    } else if (nhietdo < 6 && nhietdo >= 0) {
                        imgThoiTietHanoi.setImageResource(R.drawable.winter);
                    } else if (nhietdo < 0) {
                        imgThoiTietHanoi.setImageResource(R.drawable.snow);
                    }


                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());
        mMaps = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng latLngHCM = new LatLng(10.782773,106.700035);
        LatLng latLngHN = new LatLng( 21.021938,105.81881);


        googleMap.addMarker(new MarkerOptions().position(latLngHCM).title("Ho Chi Minh city"))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.flag));;

        googleMap.addMarker(new MarkerOptions().position(latLngHN).title("Ha Noi city"))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.flag));
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(latLngHN).zoom(6).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
        googleMap.addCircle(new CircleOptions().center(latLngHCM).radius(2000).strokeColor(Color.BLACK).strokeWidth(2));

    }
}
