package com.haiph.oysi.model.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentLocation {
    @SerializedName("nearest_city")
    @Expose
    String nearest_city;

    @Override
    public String toString() {
        return nearest_city;
    }
}
