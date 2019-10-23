
package com.haiph.oysi.model.datacity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LittleCity {


    @SerializedName("data")
    @Expose
    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
