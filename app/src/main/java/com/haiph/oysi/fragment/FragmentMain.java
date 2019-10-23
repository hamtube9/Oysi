package com.haiph.oysi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.adapter.AdapterMainFragment;
import com.haiph.oysi.model.country.Country;
import com.haiph.oysi.response.CountryResponse;
import com.haiph.oysi.service.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMain extends Fragment {
    SearchView searchView;
    RecyclerView rcView;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";
    List<Country> dulieu;
    AdapterMainFragment adapter;
    ArrayList<Country> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        searchView = view.findViewById(R.id.searchView);
        rcView = view.findViewById(R.id.rcViewMain);
        rcView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = getSpacecraft();
        adapter = new AdapterMainFragment(getActivity(), list);
        rcView.setAdapter(adapter);

        getData();

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

    private ArrayList<Country> getSpacecraft() {


        return list;
    }

    private void getData() {
        RetrofitService.getInstance().getAllCountry(key).enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    dulieu = response.body().data;
                    list.clear();
                    list.addAll(dulieu);
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {

            }
        });
    }
}
