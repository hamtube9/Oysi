package com.haiph.oysi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.haiph.oysi.R;
import com.haiph.oysi.adapter.AdapterListCountry;
import com.haiph.oysi.model.country.Country;
import com.haiph.oysi.service.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCountry extends AppCompatActivity {
    RecyclerView rcViewCountry;
    ArrayList<Country> list;
    AdapterListCountry adapterListCountry;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_country);
        rcViewCountry=findViewById(R.id.rcListCountry);
        list=new ArrayList<>();
        rcViewCountry.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterListCountry=new AdapterListCountry(list, getApplicationContext(), new AdapterListCountry.ItemListener() {
            @Override
            public void ItemOnclickListener(int position) {

            }
        });
        rcViewCountry.setAdapter(adapterListCountry);

        getAllCountry();
    }

    private void getAllCountry() {
        RetrofitService.getInstance().getAllCountry(key).enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful() && response.code()==200){
                   list.addAll(response.body());
                    Log.e("country","country :"+response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });
    }
}
