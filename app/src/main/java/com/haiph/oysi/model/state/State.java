package com.haiph.oysi.model.state;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {
    @SerializedName("state")
    @Expose
    public String state;

    @Override
    public String toString() {
        return state ;
    }
}
