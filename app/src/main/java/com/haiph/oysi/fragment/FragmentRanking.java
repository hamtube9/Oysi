package com.haiph.oysi.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.adapter.AdapterRanking;
import com.haiph.oysi.model.ranking.Datum;
import com.haiph.oysi.model.ranking.World;
import com.haiph.oysi.response.RankingResponse;
import com.haiph.oysi.service.RequestRetrofit;
import com.haiph.oysi.service.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRanking extends Fragment {
    private RecyclerView rcViewRank;
    private ArrayList<Datum> list = new ArrayList<>();
    private AdapterRanking adapter;
    private String key = "61abeb98-9035-4d63-86fb-6dfaa3992737";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        rcViewRank = view.findViewById(R.id.rcViewRank);
        adapter = new AdapterRanking(getActivity(), list);
        rcViewRank.setAdapter(adapter);
        rcViewRank.setLayoutManager(new LinearLayoutManager(getActivity()));

       getQualityRanking();
        return view;
    }

    private void getQualityRanking() {
        RequestRetrofit.getRequest().getRanking().enqueue(new Callback<RankingResponse>() {
            @Override
            public void onResponse(Call<RankingResponse> call, Response<RankingResponse> response) {
                if (response.isSuccessful()){
                    list.addAll(response.body().data);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RankingResponse> call, Throwable t) {

            }
        });
    }

}
