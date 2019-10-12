package com.haiph.oysi.model.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("city")
    @Expose
    public String city;

    @Override
    public String toString() {
        return city;
    }
}
