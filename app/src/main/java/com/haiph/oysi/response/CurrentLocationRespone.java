package com.haiph.oysi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.haiph.oysi.model.location.CurrentLocation;
import com.haiph.oysi.model.location.Data;

import java.util.ArrayList;
import java.util.List;

public class CurrentLocationRespone extends BaseResponse {
    @SerializedName("data")
    @Expose
    public Data data;


}
