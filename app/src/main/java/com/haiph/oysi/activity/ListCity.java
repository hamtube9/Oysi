package com.haiph.oysi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.haiph.oysi.R;
import com.haiph.oysi.adapter.AdapterListCity;
import com.haiph.oysi.model.city.City;
import com.haiph.oysi.response.CityResponse;
import com.haiph.oysi.service.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCity extends AppCompatActivity {
    RecyclerView rcListCity;
    AdapterListCity adapter;
    List<City> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_city);
        rcListCity = findViewById(R.id.rcListCity);
        list = new ArrayList<>();
        rcListCity.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new AdapterListCity(list, getApplicationContext(), new AdapterListCity.ItemListener() {
            @Override
            public void ItemOnclickListener() {

            }
        });
        rcListCity.setAdapter(adapter);

        getAllCity();
    }

    private void getAllCity() {
        Intent i = getIntent();
        String key = i.getStringExtra("key");
        String country = i.getStringExtra("country");
        String state = i.getStringExtra("state");
        Log.e("dataCity","key "+ key +"/n country "+country+" /n state "+state);

        RetrofitService.getInstance().getAllCity(state,country,key).enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful()&& response.code()==200){
                    list.addAll(response.body().data);
                    adapter.notifyDataSetChanged();

                    Log.e("city", ""+response.body().data);
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });
    }
}
