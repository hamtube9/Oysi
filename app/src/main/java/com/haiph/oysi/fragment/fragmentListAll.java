package com.haiph.oysi.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import static android.content.Context.MODE_PRIVATE;

public class fragmentListAll extends Fragment {
    FloatingActionButton fabSwitcher;
    RecyclerView rcViewCountry;
    List<Country> list;
    EditText search;
    ConstraintLayout rootLayout;
    AdapterListCountry adapterListCountry;
    String key = "643d17a2-2def-469d-8c9b-bd90c5a7a550";
    CharSequence text="";
    boolean isDark = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_all, container, false);

        search=view.findViewById(R.id.search_input);
        rootLayout=view.findViewById(R.id.root);
        fabSwitcher=view.findViewById(R.id.fab_switcher);
        rcViewCountry = view.findViewById(R.id.rcListCountry);
        list = new ArrayList<>();
        adapterListCountry = new AdapterListCountry(list, getContext(),isDark);
        rcViewCountry.setLayoutManager(new LinearLayoutManager(getContext()));
        rcViewCountry.setAdapter(adapterListCountry);
        adapterListCountry.notifyDataSetChanged();
        isDark = getThemeStatePref();
        if(isDark) {
            // dark theme is on

            search.setBackgroundResource(R.drawable.search_input_dark_style);
            search.setHintTextColor(Color.WHITE);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.black));

        }
        else
        {
            // light theme is on
            search.setBackgroundResource(R.drawable.search_input_type);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));

        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterListCountry.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fabSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDark = !isDark ;
                if (isDark) {

                    rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                    search.setBackgroundResource(R.drawable.search_input_dark_style);

                }
                else {
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    search.setBackgroundResource(R.drawable.search_input_type);
                }

                adapterListCountry = new AdapterListCountry(list, getActivity(),isDark);
                if (!search.toString().isEmpty()){

                    adapterListCountry.getFilter().filter(text);

                }
                rcViewCountry.setAdapter(adapterListCountry);
                saveThemeStatePref(isDark);




            }
        });
        getAllCountry();
        return view;
    }

    private void getAllCountry() {
        RetrofitService.getInstance().getAllCountry(key).enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    list.addAll(response.body().data);
                    adapterListCountry.notifyDataSetChanged();
           //         Log.e("country", "country :" + response.body().data.toString());
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {

            }
        });


    }
    private void saveThemeStatePref(boolean isDark) {

        SharedPreferences pref = getContext().getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDark",isDark);
        editor.commit();
    }

    private boolean getThemeStatePref () {

        SharedPreferences pref = getContext().getSharedPreferences("myPref",MODE_PRIVATE);
        boolean isDark = pref.getBoolean("isDark",false) ;
        return isDark;

    }
}
