package com.haiph.oysi.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.haiph.oysi.R;
import com.haiph.oysi.response.LittleCityResponse;
import com.haiph.oysi.service.RetrofitService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragmentMyCountry extends Fragment {
    TextView tvHanoi,tvHCM;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_country, container, false);
        tvHanoi=view.findViewById(R.id.tvHanoi);
        tvHCM=view.findViewById(R.id.tvHCM);
        getHanoi();
        getHCM();
        return view;
    }

    private void getHCM() {
        RetrofitService.getInstance().getLittleCity("Ho Chi Minh City","Ho Chi Minh City","Vietnam",key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful() && response.code()==200){
                    Log.e("mycountry",response.body().data.getCity()+"");
                    tvHCM.setText(response.body().data.getCity());

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }

    private void getHanoi() {
        RetrofitService.getInstance().getLittleCity("Hanoi","Hanoi","Vietnam",key).enqueue(new Callback<LittleCityResponse>() {
            @Override
            public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                if (response.isSuccessful() && response.code()==200){
                    Log.e("mycountry",response.body().data.getCity()+"");
                    tvHanoi.setText(response.body().data.getCity());

                }
            }

            @Override
            public void onFailure(Call<LittleCityResponse> call, Throwable t) {

            }
        });
    }
}
