package com.haiph.oysi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.haiph.oysi.model.country.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public List<Country> data = new ArrayList<>();
}
