package com.haiph.oysi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.haiph.oysi.model.city.City;

import java.util.ArrayList;
import java.util.List;

public class CityResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public List<City> data = new ArrayList<>();
}
