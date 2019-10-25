package com.haiph.oysi.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.adapter.AdapterFragmentCity;
import com.haiph.oysi.model.city.City;
import com.haiph.oysi.model.datacity.LittleCity;
import com.haiph.oysi.response.CityResponse;
import com.haiph.oysi.response.LittleCityResponse;
import com.haiph.oysi.service.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCity extends Fragment {
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";
    RecyclerView recyclerView;
    SearchView searchView;
    ArrayList<City> list = new ArrayList<>();
    List<City> cityList;
    AdapterFragmentCity adapter;
    CircleImageView back;
    String country;
    String state;
    TextView tvCity, tvNhietDoCity,tvAQICity,tvTipCity;
    ImageView emotionCity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
         country = b.getString("country");
         state = b.getString("state");
        Log.e("dataCity",country+" "+state+"");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        recyclerView=view.findViewById(R.id.rcViewCity);
        searchView=view.findViewById(R.id.svCity);
        back=view.findViewById(R.id.backCity);
        tvAQICity=view.findViewById(R.id.tvAQICity);
        tvCity=view.findViewById(R.id.tvCity);
        tvNhietDoCity=view.findViewById(R.id.tvNhietDoCity);
        tvTipCity=view.findViewById(R.id.tvTipCity);
        emotionCity=view.findViewById(R.id.imgEmotionCity);

        onClick();
        setAdapter();
        getCity();
        return view;
    }

    private void setAdapter() {
        list=getCityCraft();
        adapter=new AdapterFragmentCity(list, getActivity(), new AdapterFragmentCity.itemOnclick() {
            @Override
            public void itemOnclick(int position) {
                City c = list.get(position);
                String city = c.city;
                Log.e("city",city);

                RetrofitService.getInstance().getLittleCity(city,state,country,key).enqueue(new Callback<LittleCityResponse>() {
                    @Override
                    public void onResponse(Call<LittleCityResponse> call, Response<LittleCityResponse> response) {
                        if (response.isSuccessful()){
                            String aqi = String.valueOf(response.body().data.getCurrent().getPollution().getAqius());
                            String nhietdo = String.valueOf(response.body().data.getCurrent().getWeather().getTp());
                            String tencity = response.body().data.getCity();

                            Log.e("khongkhi",nhietdo+" "+aqi);
                            tvAQICity.setText(aqi);
                            tvNhietDoCity.setText(nhietdo);
                            tvCity.setText(tencity);

                        }
                    }

                    @Override
                    public void onFailure(Call<LittleCityResponse> call, Throwable t) {

                    }
                });
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);

    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new FragmentStateofCountry();
                Bundle bundle = new Bundle();
                bundle.putString("country",country);
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.framelayout,fragment);
                fragmentTransaction.commit();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }



    private ArrayList<City> getCityCraft(){
        return list;
    }

    private void getCity() {


        RetrofitService.getInstance().getAllCity(state,country,key).enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful()){
                    cityList = response.body().data;
                    list.addAll(cityList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });

    }
}
