package com.haiph.oysi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.haiph.oysi.model.streets.Data;

public class StreetsResponse  extends BaseResponse{
    @SerializedName("data")
    @Expose
    public Data data;
}
