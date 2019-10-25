package com.haiph.oysi.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.adapter.AdapterFragmentCity;
import com.haiph.oysi.model.city.City;
import com.haiph.oysi.response.CityResponse;
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
    AdapterFragmentCity adapter;
    CircleImageView back;
    List<City> cityList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        recyclerView=view.findViewById(R.id.rcViewCity);
        searchView=view.findViewById(R.id.svCity);
        back=view.findViewById(R.id.backCity);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new FragmentCountryInTheWorld();
                fragmentTransaction.replace(R.id.framelayout,fragment);
                fragmentTransaction.commit();
            }
        });
        list=getCityCraft();
        adapter=new AdapterFragmentCity(list, getActivity(), new AdapterFragmentCity.itemOnclick() {
            @Override
            public void itemOnclick(int position) {

            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);

        getCity();

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
        return view;
    }
    private ArrayList<City> getCityCraft(){
        return list;
    }

    private void getCity() {
        Bundle b = getArguments();
        String country = b.getString("country");
        String state = b.getString("state");
        Log.e("dataCity",country+" "+state+"");

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
