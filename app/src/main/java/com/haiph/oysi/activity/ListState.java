package com.haiph.oysi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.haiph.oysi.R;
import com.haiph.oysi.adapter.AdapterListState;
import com.haiph.oysi.model.state.State;
import com.haiph.oysi.response.StateResponse;
import com.haiph.oysi.service.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListState extends AppCompatActivity {
    List<State> list;
    AdapterListState adapter;
    RecyclerView rcListState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_state);

        rcListState=findViewById(R.id.rcListState);
        list=new ArrayList<>();
        Intent intent= getIntent();
        final String country = intent.getStringExtra("country");
        final String key = intent.getStringExtra("key");
        adapter=new AdapterListState(list, getApplicationContext(), new AdapterListState.ItemListener() {
            @Override
            public void ItemOnclickListener(int position) {
                Intent i =new Intent(ListState.this,ListCity.class);
                State state=list.get(position);
                i.putExtra("country",country);
                i.putExtra("key",key);
                i.putExtra("state",state.state);
                startActivity(i);
            }
        });
        rcListState.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcListState.setAdapter(adapter);
        getAllState();

    }

    private void getAllState() {
        Intent intent= getIntent();
        String country = intent.getStringExtra("country");
        String key = intent.getStringExtra("key");
        Log.e("dataCountry","country : "+country+ "/n key :"+key);
        RetrofitService.getInstance().getAllState(country,key).enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                if (response.isSuccessful() && response.code()==200){
                    list.addAll(response.body().data);
                    adapter.notifyDataSetChanged();
                    Log.e("state", "state : " +response.body().data.toString());
                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {

            }
        });
    }
}
