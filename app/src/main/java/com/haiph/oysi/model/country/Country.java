
package com.haiph.oysi.model.country;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("country")
    @Expose
    public String country;

    @Override
    public String toString() {
        return country;
    }
}
