package com.haiph.oysi.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
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
import com.haiph.oysi.activity.MapsActivity;
import com.haiph.oysi.response.CurrentLocationRespone;
import com.haiph.oysi.response.LittleCityResponse;
import com.haiph.oysi.response.StreetsResponse;
import com.haiph.oysi.service.RetrofitService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FragmentMyCountry extends Fragment implements OnMapReadyCallback {
    TextView tvHanoi, tvHCM;
    TextView tvAQIHanoi, tvNhietDoHanoi, tvAQIHCM, tvNhietDoHCM;
    TextView tvTipHanoi, tvTipHCM;
    ImageView imgThoiTietHanoi, imgEmotionHanoi;
    ImageView imgThoiTietHCM, imgEmotionHCM;


    Button sharebtn;
    ShareDialog shareDialog;
    CallbackManager callbackManager;

    String key = "61abeb98-9035-4d63-86fb-6dfaa3992737";
    int aqi, nhietdo;
    GoogleMap mMaps;
    MapView mapView;
    Button btngetlocation;
    LinearLayout bgAQIHCM, bgAQIHCM1, bgAQIHanoi, bgAQIHanoi1, bgImageHanoi, bgImageHCM;
    int AQIcaugiay, AQIbadinh, AQIquocoai, AQIsocson, AQItayho, AQIthachthat, AQItrauquy, AQihbt, AQIhoankiem, AQIDongDa, AQIhangdau, AQItanmai, AQItrunghoa;

    int citiHanoi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_country, container, false);
        getQuanCauGiay();
        getQuocOai();
        getHanoi();
        getTrungHoa();
        getHCM();
        getQuocOai();
        getSocSon();
        getThachThat();
        getlittleHanoi();
        getTayHo();
        getTrauQuy();
        getHaiBaTrung();
        getHoanKiem();
        getHangDau();
        getTanMai();
        getDongDa();
        btngetlocation = view.findViewById(R.id.btngetlocation);
        btngetlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MapsActivity.class);
                startActivity(i);
            }
        });


        tvHanoi = view.findViewById(R.id.tvHanoi);
        tvAQIHanoi = view.findViewById(R.id.tvAQIHanoi);
        tvTipHanoi = view.findViewById(R.id.tvTipHanoi);
        imgEmotionHanoi = view.findViewById(R.id.imgEmotionHanoi);
        imgThoiTietHanoi = view.findViewById(R.id.imgThoiTietHanoi);
        tvNhietDoHanoi = view.findViewById(R.id.tvNhietDoHanoi);
        bgAQIHanoi = view.findViewById(R.id.bgAQIHanoi);
        bgAQIHanoi1 = view.findViewById(R.id.bgAQIHanoi1);
        bgImageHanoi = view.findViewById(R.id.bgImgHanoi);
        sharebtn=view.findViewById(R.id.share);

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


        FacebookSdk.sdkInitialize(this.getContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqiHanoi",MODE_PRIVATE);
        int aqiHanoiCity = sharedPreferences.getInt("aqiHanoi",0);
         citiHanoi = Integer.parseInt(tvAQIHanoi.getText().toString());
        Log.e("c",citiHanoi+"");

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ct = "Mức độ ô nhiễm đang ở mức "+citiHanoi+" bạn nên chú ý nhé";
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setQuote(ct)
                        .setContentUrl(Uri.parse("https://www.airvisual.com/vietnam/hanoi"))
                        .build();
                if(ShareDialog.canShow(ShareLinkContent.class)){
                    shareDialog.show(content);
                }
            }
        });

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
                    Log.e("aqiHanoi", aqi + "");

                    aqi = response.body().data.getCurrent().getPollution().getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqiHanoi",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("aqiHanoi",aqi);
                    tvAQIHanoi.setText(aqi+"");
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
        LatLng latLngHCM = new LatLng(10.782773, 106.700035);
        LatLng latLngHN = new LatLng(21.021938, 105.81881);
        LatLng caugiay = new LatLng(21.03092148, 105.78276157);
        LatLng badinh = new LatLng(21.021938, 105.81881);
        LatLng quocOai = new LatLng(20.99019, 105.64088);
        LatLng SocSon = new LatLng(21.25782, 105.84894);
        LatLng thachthat = new LatLng(21.014693, 105.529948);
        LatLng tayho = new LatLng(21.06895, 105.81053);
        LatLng trauquy = new LatLng(21.0195, 105.93694);
        LatLng haibatrung = new LatLng(21.012252, 105.850843);
        LatLng tanmai = new LatLng(20.9883, 105.8549);
        LatLng hangdau = new LatLng(21.0399, 105.8473);
        LatLng dongda = new LatLng(21.012702, 105.828076);
        LatLng hoankiem = new LatLng(21.029469, 105.852175);
        LatLng trunghoa = new LatLng(21.010255, 105.799222);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqi", MODE_PRIVATE);
        AQIcaugiay = sharedPreferences.getInt("aqi", 0);
        SharedPreferences spBadinh = getActivity().getSharedPreferences("aqibadinh", MODE_PRIVATE);
        AQIbadinh = spBadinh.getInt("aqi", 0);
        SharedPreferences spQuocOai = getActivity().getSharedPreferences("aqiQuocOai", MODE_PRIVATE);
        AQIquocoai = spQuocOai.getInt("aqi", 0);
        SharedPreferences spTayHo = getActivity().getSharedPreferences("aqiTayHo", MODE_PRIVATE);
        AQItayho = spTayHo.getInt("aqi", 0);
        SharedPreferences spSocSon = getActivity().getSharedPreferences("aqiSocSon", MODE_PRIVATE);
        AQIsocson = spSocSon.getInt("aqi", 0);
        SharedPreferences spHoanKiem = getActivity().getSharedPreferences("aqiHoankiem", MODE_PRIVATE);
        AQIhoankiem = spHoanKiem.getInt("aqi", 0);
        SharedPreferences spTrauQuy = getActivity().getSharedPreferences("aqiTrauquy", MODE_PRIVATE);
        AQItrauquy = spTrauQuy.getInt("aqi", 0);
        SharedPreferences spThachThat = getActivity().getSharedPreferences("aqiThachthat", MODE_PRIVATE);
        AQIthachthat = spThachThat.getInt("aqi", 0);
        SharedPreferences spHBT = getActivity().getSharedPreferences("aqiHBT", MODE_PRIVATE);
        AQihbt = spHBT.getInt("aqi", 0);
        SharedPreferences spDongDa = getActivity().getSharedPreferences("aqiDongda", MODE_PRIVATE);
        AQIDongDa = spDongDa.getInt("aqi", 0);
        SharedPreferences spHangDau = getActivity().getSharedPreferences("aqihangdau", MODE_PRIVATE);
        AQIhangdau = spHangDau.getInt("aqi", 0);
        SharedPreferences spTanmai = getActivity().getSharedPreferences("aqiTanMai", MODE_PRIVATE);
        AQItanmai = spTanmai.getInt("aqi", 0);
        SharedPreferences spTrungHoa = getActivity().getSharedPreferences("aqitrunghoa", MODE_PRIVATE);
        AQItrunghoa = spTrungHoa.getInt("aqi", 0);

        googleMap.addMarker(new MarkerOptions().position(latLngHCM).title("Ho Chi Minh city"))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.flag));
        googleMap.addMarker(new MarkerOptions().position(caugiay).title("Cầu Giấy"));
        googleMap.addMarker(new MarkerOptions().position(badinh).title("Ba đình"));
        googleMap.addMarker(new MarkerOptions().position(quocOai).title("Quốc Oai"));
        googleMap.addMarker(new MarkerOptions().position(SocSon).title("Sóc Sơn"));
        googleMap.addMarker(new MarkerOptions().position(thachthat).title("Thạch Thất"));
        googleMap.addMarker(new MarkerOptions().position(trauquy).title("Trâu Quỳ"));
        googleMap.addMarker(new MarkerOptions().position(trunghoa).title("Trung Hòa"));
        googleMap.addMarker(new MarkerOptions().position(tanmai).title("Tân Mai"));
        googleMap.addMarker(new MarkerOptions().position(tayho).title("Tây Hồ"));
        googleMap.addMarker(new MarkerOptions().position(haibatrung).title("Hai Bà Trưng"));
        googleMap.addMarker(new MarkerOptions().position(hangdau).title("Hàng Đậu"));
        googleMap.addMarker(new MarkerOptions().position(dongda).title("Đống Đa"));
        googleMap.addMarker(new MarkerOptions().position(hoankiem).title("Hoàn Kiếm"));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(hoankiem).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
        googleMap.addCircle(new CircleOptions().center(latLngHCM).radius(20000).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(latLngHN).radius(20000).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(caugiay).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(badinh).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(quocOai).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(SocSon).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(thachthat).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(tayho).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(haibatrung).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(hangdau).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(dongda).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(hoankiem).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(tanmai).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(trauquy).radius(700).strokeColor(Color.BLACK).strokeWidth(2));
        googleMap.addCircle(new CircleOptions().center(trunghoa).radius(700).strokeColor(Color.BLACK).strokeWidth(2));


        if (AQIcaugiay < 50) {
            CircleOptions COcaugiay = new CircleOptions().center(caugiay).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COcaugiay);

        } else if (AQIcaugiay >= 50 && AQIcaugiay < 100) {
            CircleOptions COcaugiay = new CircleOptions().center(caugiay).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COcaugiay);
        } else if (AQIcaugiay >= 100 && AQIcaugiay < 150) {
            CircleOptions COcaugiay = new CircleOptions().center(caugiay).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COcaugiay);
        } else if (AQIcaugiay >= 150 && AQIcaugiay < 200) {
            CircleOptions COcaugiay = new CircleOptions().center(caugiay).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COcaugiay);
        } else if (AQIcaugiay >= 200) {
            CircleOptions COcaugiay = new CircleOptions().center(caugiay).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COcaugiay);
        }


        Log.e("AQIbadinh", AQIbadinh + "");

        if (AQIbadinh < 50) {
            CircleOptions CObadinh = new CircleOptions().center(badinh).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(CObadinh);

        } else if (AQIbadinh >= 50 && AQIbadinh < 100) {
            CircleOptions CObadinh = new CircleOptions().center(badinh).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(CObadinh);
        } else if (AQIbadinh >= 100 && AQIbadinh < 150) {
            CircleOptions CObadinh = new CircleOptions().center(badinh).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(CObadinh);
        } else if (AQIbadinh >= 150 && AQIbadinh < 200) {
            CircleOptions CObadinh = new CircleOptions().center(badinh).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(CObadinh);
        } else if (AQIbadinh >= 200) {
            CircleOptions CObadinh = new CircleOptions().center(badinh).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(CObadinh);
        }


        if (AQIquocoai < 50) {
            CircleOptions COquocoai = new CircleOptions().center(quocOai).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COquocoai);

        } else if (AQIquocoai >= 50 && AQIquocoai < 100) {
            CircleOptions COquocoai = new CircleOptions().center(quocOai).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COquocoai);
        } else if (AQIquocoai >= 100 && AQIquocoai < 150) {
            CircleOptions COquocoai = new CircleOptions().center(quocOai).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COquocoai);
        } else if (AQIquocoai >= 150 && AQIquocoai < 200) {
            CircleOptions COquocoai = new CircleOptions().center(quocOai).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COquocoai);
        } else if (AQIquocoai >= 200) {
            CircleOptions COquocoai = new CircleOptions().center(quocOai).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COquocoai);
        }


        if (AQIsocson < 50) {
            CircleOptions COsocson = new CircleOptions().center(SocSon).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COsocson);

        } else if (AQIsocson >= 50 && AQIsocson < 100) {
            CircleOptions COsocson = new CircleOptions().center(SocSon).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COsocson);
        } else if (AQIsocson >= 100 && AQIsocson < 150) {
            CircleOptions COsocson = new CircleOptions().center(SocSon).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COsocson);
        } else if (AQIsocson >= 150 && AQIsocson < 200) {
            CircleOptions COsocson = new CircleOptions().center(SocSon).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COsocson);
        } else if (AQIquocoai >= 200) {
            CircleOptions COsocson = new CircleOptions().center(SocSon).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COsocson);
        }



        if (AQItayho < 50) {
            CircleOptions COtayho = new CircleOptions().center(tayho).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtayho);

        } else if (AQItayho >= 50 && AQItayho < 100) {
            CircleOptions COtayho = new CircleOptions().center(tayho).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtayho);
        } else if (AQItayho >= 100 && AQItayho < 150) {
            CircleOptions COtayho = new CircleOptions().center(tayho).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtayho);
        } else if (AQItayho >= 150 && AQItayho < 200) {
            CircleOptions COtayho = new CircleOptions().center(tayho).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtayho);
        } else if (AQItayho >= 200) {
            CircleOptions COtayho = new CircleOptions().center(tayho).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtayho);
        }

        Log.e("AQIthachthat", AQIthachthat + "");

        if (AQIthachthat < 50) {
            CircleOptions COthachthat = new CircleOptions().center(thachthat).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COthachthat);

        } else if (AQIthachthat >= 50 && AQIthachthat < 100) {
            CircleOptions COthachthat = new CircleOptions().center(thachthat).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COthachthat);
        } else if (AQIthachthat >= 100 && AQIthachthat < 150) {
            CircleOptions COthachthat = new CircleOptions().center(thachthat).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COthachthat);
        } else if (AQIthachthat >= 150 && AQIthachthat < 200) {
            CircleOptions COthachthat = new CircleOptions().center(thachthat).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COthachthat);
        } else if (AQIthachthat >= 200) {
            CircleOptions COthachthat = new CircleOptions().center(thachthat).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COthachthat);
        }

        if (AQItrauquy < 50) {
            CircleOptions COtrauquy = new CircleOptions().center(trauquy).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrauquy);

        } else if (AQItrauquy >= 50 && AQItrauquy < 100) {
            CircleOptions COtrauquy = new CircleOptions().center(trauquy).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrauquy);
        } else if (AQItrauquy >= 100 && AQItrauquy < 150) {
            CircleOptions COtrauquy = new CircleOptions().center(trauquy).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrauquy);
        } else if (AQItrauquy >= 150 && AQItrauquy < 200) {
            CircleOptions COtrauquy = new CircleOptions().center(trauquy).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrauquy);
        } else if (AQItrauquy >= 200) {
            CircleOptions COtrauquy = new CircleOptions().center(trauquy).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrauquy);
        }

        if (AQihbt < 50) {
            CircleOptions COtrauquy = new CircleOptions().center(haibatrung).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrauquy);

        } else if (AQihbt >= 50 && AQihbt < 100) {
            CircleOptions COhbt = new CircleOptions().center(haibatrung).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhbt);
        } else if (AQihbt >= 100 && AQihbt < 150) {
            CircleOptions COhbt = new CircleOptions().center(haibatrung).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhbt);
        } else if (AQihbt >= 150 && AQihbt < 200) {
            CircleOptions COhbt = new CircleOptions().center(haibatrung).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhbt);
        } else if (AQihbt >= 200) {
            CircleOptions COhbt = new CircleOptions().center(haibatrung).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhbt);
        }


        if (AQIhoankiem < 50) {
            CircleOptions COtrauquy = new CircleOptions().center(hoankiem).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrauquy);

        } else if (AQIhoankiem >= 50 && AQIhoankiem < 100) {
            CircleOptions COhoankiem = new CircleOptions().center(hoankiem).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhoankiem);
        } else if (AQIhoankiem >= 100 && AQIhoankiem < 150) {
            CircleOptions COhoankiem = new CircleOptions().center(hoankiem).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhoankiem);
        } else if (AQIhoankiem >= 150 && AQIhoankiem < 200) {
            CircleOptions COhoankiem = new CircleOptions().center(hoankiem).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhoankiem);
        } else if (AQIhoankiem >= 200) {
            CircleOptions COhoankiem = new CircleOptions().center(hoankiem).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhoankiem);
        }



        if (AQIDongDa < 50) {
            CircleOptions COdongda = new CircleOptions().center(dongda).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COdongda);

        } else if (AQIDongDa >= 50 && AQIDongDa < 100) {
            CircleOptions COdongda = new CircleOptions().center(dongda).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COdongda);
        } else if (AQIDongDa >= 100 && AQIDongDa < 150) {
            CircleOptions COdongda = new CircleOptions().center(dongda).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COdongda);
        } else if (AQIDongDa >= 150 && AQIDongDa < 200) {
            CircleOptions COdongda = new CircleOptions().center(dongda).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COdongda);
        } else if (AQIDongDa >= 200) {
            CircleOptions COdongda = new CircleOptions().center(dongda).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COdongda);
        }


        if (AQIhangdau < 50) {
            CircleOptions COhangdau = new CircleOptions().center(hangdau).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhangdau);

        } else if (AQIhangdau >= 50 && AQIhangdau < 100) {
            CircleOptions COhangdau = new CircleOptions().center(hangdau).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhangdau);
        } else if (AQIhangdau >= 100 && AQIhangdau < 150) {
            CircleOptions COhangdau = new CircleOptions().center(hangdau).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhangdau);
        } else if (AQIhangdau >= 150 && AQIhangdau < 200) {
            CircleOptions COhangdau = new CircleOptions().center(hangdau).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhangdau);
        } else if (AQIhangdau >= 200) {
            CircleOptions COhangdau = new CircleOptions().center(hangdau).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COhangdau);
        }


        if (AQItanmai < 50) {
            CircleOptions COtanmai = new CircleOptions().center(tanmai).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtanmai);

        } else if (AQItanmai >= 50 && AQItanmai < 100) {
            CircleOptions COtanmai = new CircleOptions().center(tanmai).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtanmai);
        } else if (AQItanmai >= 100 && AQItanmai < 150) {
            CircleOptions COtanmai = new CircleOptions().center(tanmai).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtanmai);
        } else if (AQItanmai >= 150 && AQItanmai < 200) {
            CircleOptions COtanmai = new CircleOptions().center(tanmai).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtanmai);
        } else if (AQItanmai >= 200) {
            CircleOptions COtanmai = new CircleOptions().center(tanmai).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtanmai);
        }


        if (AQItrunghoa < 50) {
            CircleOptions COtrunghoa = new CircleOptions().center(trunghoa).radius(700).
                    fillColor(Color.parseColor("#a8e05f")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrunghoa);

        } else if (AQItrunghoa >= 50 && AQItrunghoa < 100) {
            CircleOptions COtrunghoa = new CircleOptions().center(trunghoa).radius(700).
                    fillColor(Color.parseColor("#fdd74b")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrunghoa);
        } else if (AQItrunghoa >= 100 && AQItrunghoa < 150) {
            CircleOptions COtrunghoa = new CircleOptions().center(trunghoa).radius(700).
                    fillColor(Color.parseColor("#fe9b57")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrunghoa);
        } else if (AQItrunghoa >= 150 && AQItrunghoa < 200) {
            CircleOptions COtrunghoa = new CircleOptions().center(trunghoa).radius(700).
                    fillColor(Color.parseColor("#fe6a69")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrunghoa);
        } else if (AQItrunghoa >= 200) {
            CircleOptions COtrunghoa = new CircleOptions().center(trunghoa).radius(700).
                    fillColor(Color.parseColor("#940045")).
                    strokeColor(Color.BLACK).
                    strokeWidth(2);
            googleMap.addCircle(COtrunghoa);
        }
    }


    public void getQuanCauGiay() {
        RetrofitService.getInstance().getLittleCity("Cau Giay", "Hanoi", "Vietnam", key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()) {
                    List<Double> array = response.body().data.getLocation().getCoordinates();

                    int nhietdoCauGiay = response.body().data.getCurrent().getWeather().getTp();
                    int aqiCauGiay = response.body().data.getCurrent().getPollution().getAqius();

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqi", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiCauGiay);
                    editor.commit();


                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }

    public void getlittleHanoi() {
        RetrofitService.getInstance().getLittleCity("Hanoi", "Hanoi", "Vietnam", key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()) {
                    int aqiBadinh = response.body().data.getCurrent().getPollution().getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqibadinh", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiBadinh);
                    editor.commit();


                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }

    public void getQuocOai() {
        RetrofitService.getInstance().getLittleCity("Quoc Oai", "Hanoi", "Vietnam", key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()) {
                    int aqiQuocOai = response.body().data.getCurrent().getPollution().getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqiQuocOai", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiQuocOai);
                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }

    public void getSocSon() {
        RetrofitService.getInstance().getLittleCity("Soc Son", "Hanoi", "Vietnam", key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()) {
                    int aqiSocSon = response.body().data.getCurrent().getPollution().getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqiSocSon", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiSocSon);

                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }

    public void getTayHo() {
        RetrofitService.getInstance().getLittleCity("Tay Ho", "Hanoi", "Vietnam", key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()) {

                    int aqiTayHo = response.body().data.getCurrent().getPollution().getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqiTayHo", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiTayHo);

                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }

    public void getThachThat() {
        RetrofitService.getInstance().getLittleCity("Thach That", "Hanoi", "Vietnam", key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()) {
                    int aqiThachThat = response.body().data.getCurrent().getPollution().getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqiThachthat", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiThachThat);

                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }

    public void getTrauQuy() {
        RetrofitService.getInstance().getLittleCity("Trau Quy", "Hanoi", "Vietnam", key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful()) {
                    int aqiTrauQuy = response.body().data.getCurrent().getPollution().getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqiTrauquy", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiTrauQuy);
                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }


    public void getHaiBaTrung() {
        RetrofitService.getInstance().getStreets("21.012252", "105.850843", key).enqueue(new Callback<StreetsResponse>() {
            @Override
            public void onResponse(Call<StreetsResponse> call, Response<StreetsResponse> response) {
                if (response.isSuccessful()) {
                    int aqiHaibatrung = response.body().data.getForecasts().get(0).getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqiHBT", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiHaibatrung);
                    Log.e("aqihbt", aqiHaibatrung + "");
                    editor.commit();

                }
            }
// ngon đc rồi
            @Override
            public void onFailure(Call<StreetsResponse> call, Throwable t) {

            }
        });
    }

    public void getTanMai() {
        RetrofitService.getInstance().getStreets("20.9883", "105.8549", key).enqueue(new Callback<StreetsResponse>() {
            @Override
            public void onResponse(Call<StreetsResponse> call, Response<StreetsResponse> response) {
                if (response.isSuccessful()) {
                    int aqiDinhCong =  response.body().data.getForecasts().get(0).getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqiTanMai", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiDinhCong);
                    Log.e("aqitanmai", aqiDinhCong + "");
                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<StreetsResponse> call, Throwable t) {

            }
        });
    }


    public void getHangDau() {
        RetrofitService.getInstance().getStreets("21.039379", "105.837271", key).enqueue(new Callback<StreetsResponse>() {
            @Override
            public void onResponse(Call<StreetsResponse> call, Response<StreetsResponse> response) {
                if (response.isSuccessful()) {
                    int aqiLangChuTich =  response.body().data.getForecasts().get(0).getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqihangdau", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiLangChuTich);
                    Log.e("aqihangdau", aqiLangChuTich + "");
                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<StreetsResponse> call, Throwable t) {

            }
        });
    }

    public void getDongDa() {
        RetrofitService.getInstance().getStreets("21.012702", "105.828076", key).enqueue(new Callback<StreetsResponse>() {
            @Override
            public void onResponse(Call<StreetsResponse> call, Response<StreetsResponse> response) {
                if (response.isSuccessful()) {
                    int aqiDongDa =  response.body().data.getForecasts().get(0).getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqiDongda", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiDongDa);
                    Log.e("aqiDongDa", aqiDongDa + "");
                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<StreetsResponse> call, Throwable t) {

            }
        });
    }

    public void getHoanKiem() {
        RetrofitService.getInstance().getStreets("21.029469", "105.852175", key).enqueue(new Callback<StreetsResponse>() {
            @Override
            public void onResponse(Call<StreetsResponse> call, Response<StreetsResponse> response) {
                if (response.isSuccessful()) {
                    int aqiHoanKiem =  response.body().data.getForecasts().get(0).getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqiHoankiem", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiHoanKiem);
                    Log.e("aqiHoanKiem", aqiHoanKiem + "");
                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<StreetsResponse> call, Throwable t) {

            }
        });
    }

    public void getTrungHoa() {
        RetrofitService.getInstance().getStreets("21.010255", "105.799222", key).enqueue(new Callback<StreetsResponse>() {
            @Override
            public void onResponse(Call<StreetsResponse> call, Response<StreetsResponse> response) {
                if (response.isSuccessful()) {
                    int aqiTrungHoa =  response.body().data.getForecasts().get(0).getAqius();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aqitrunghoa", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("aqi", aqiTrungHoa);
                    Log.e("aqiTrungHoa", aqiTrungHoa + "");
                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<StreetsResponse> call, Throwable t) {

            }
        });
    }

}
