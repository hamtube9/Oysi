package com.haiph.oysi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.haiph.oysi.R;
import com.haiph.oysi.adapter.AdapterListCountry;
import com.haiph.oysi.model.country.Country;
import com.haiph.oysi.response.CountryResponse;
import com.haiph.oysi.service.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCountry extends AppCompatActivity {
    RecyclerView rcViewCountry;
    List<Country> list;
    Toolbar toolbar;
    AdapterListCountry adapterListCountry;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_country);
        toolbar=findViewById(R.id.toolbarListCountry);
        setSupportActionBar(toolbar);

        rcViewCountry = findViewById(R.id.rcListCountry);
        list = new ArrayList<>();
        rcViewCountry.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        adapterListCountry = new AdapterListCountry(list, getApplicationContext(), new AdapterListCountry.ItemListener() {
//            @Override
//            public void ItemOnclickListener(int position) {
//                Intent i=new Intent(ListCountry.this, ListState.class);
//                Country country=list.get(position);
//                i.putExtra("country",country.country);
//                i.putExtra("key",key);
//                startActivity(i);
//            }
//        });
        rcViewCountry.setAdapter(adapterListCountry);
        getAllCountry();

    }

    private void getAllCountry() {
        RetrofitService.getInstance().getAllCountry(key).enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    list.addAll(response.body().data);
                    adapterListCountry.notifyDataSetChanged();
                    Log.e("country", "country :" + response.body().data.toString());
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {

            }
        });


    }
}
