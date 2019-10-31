package com.haiph.oysi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.haiph.oysi.model.ranking.World;

import java.util.ArrayList;
import java.util.List;

public class RankingResponse extends BaseResponse{

    @SerializedName("data")
    @Expose
   public List<World> data =new ArrayList<>();
}
