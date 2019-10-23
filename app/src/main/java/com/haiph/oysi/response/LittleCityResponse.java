package com.haiph.oysi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.haiph.oysi.model.location.Data;

public class LittleCityResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public Data data;
}
