package com.haiph.oysi.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
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
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStateofCountry extends Fragment {
    RecyclerView rcViewStateofCountry;
    SearchView svStateofCountry;
    String key = "61abeb98-9035-4d63-86fb-6dfaa3992737";
    AdapterFragmentStateOfCountry adapter;
    List<State> listState;
    CircleImageView back;
    ArrayList<State> list= new ArrayList<>();
    String country;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
         country = b.getString("country");
        Log.e("getCountry",country+"");
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_state_of_country,container,false);
        rcViewStateofCountry=view.findViewById(R.id.rcViewStateofCountry);
        svStateofCountry=view.findViewById(R.id.svStateofCountry);
        back=view.findViewById(R.id.back);
        setAdapter();
        onClick();
        getState();
        return view;
    }

    private void setAdapter() {
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
                Log.e("dataState",dataCountry + " "+dataState);
                Fragment fragment = new FragmentCity();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.framelayout,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        rcViewStateofCountry.setAdapter(adapter);

    }

    public void onClick(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new FragmentCountryInTheWorld();
                fragmentTransaction.replace(R.id.framelayout,fragment);
                fragmentTransaction.commit();

            }
        });

        svStateofCountry.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void filter(String text){
        text=svStateofCountry.getQuery().toString().toLowerCase(Locale.getDefault());
        list.clear();
        if (text.length()>0){
            list.addAll(listState);
        }
        else {
            for (State state : listState){
                list.add(state);
            }
        }
        adapter.notifyDataSetChanged();
    }
    private ArrayList<State> getStateCraft(){
        return list;
    }

    private void getState() {


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
