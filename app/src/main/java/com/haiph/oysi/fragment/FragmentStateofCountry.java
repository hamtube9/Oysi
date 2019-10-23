package com.haiph.oysi.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.adapter.AdapterFragmentStateOfCountry;
import com.haiph.oysi.model.city.City;
import com.haiph.oysi.model.state.State;
import com.haiph.oysi.response.StateResponse;
import com.haiph.oysi.service.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStateofCountry extends Fragment {
    RecyclerView rcViewStateofCountry;
    SearchView svStateofCountry;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";
    AdapterFragmentStateOfCountry adapter;
    List<State> listState;
    ArrayList<State> list= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_state_of_country,container,false);
        rcViewStateofCountry=view.findViewById(R.id.rcViewStateofCountry);
        svStateofCountry=view.findViewById(R.id.svStateofCountry);
        list = getStateCraft();
        rcViewStateofCountry.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter=new AdapterFragmentStateOfCountry(list, getActivity(), new AdapterFragmentStateOfCountry.ItemOnclickState() {
            @Override
            public void ItemOnclickSelected(int position) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle b =getArguments();
                String dataCountry = b.getString("country");
                Log.e("dataCountry",dataCountry+"");

                State state = list.get(position);
                String dataState = state.state;

                Bundle bundle = new Bundle();
                bundle.putString("country",dataCountry);
                bundle.putString("state",dataState);
                Fragment fragment = new FragmentCity();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.framelayout,fragment);
                fragmentTransaction.commit();
            }
        });
        rcViewStateofCountry.setAdapter(adapter);

        getState();

        svStateofCountry.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    private ArrayList<State> getStateCraft(){
        return list;
    }

    private void getState() {
        Bundle b = getArguments();
        String country = b.getString("country");
        Log.e("getCountry",country+"");

        RetrofitService.getInstance().getAllState(country,key).enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
        if (response.isSuccessful()){
            listState = response.body().data;
            list.clear();
            list.addAll(listState);
            adapter.notifyDataSetChanged();
        }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {

            }
        });
    }
}