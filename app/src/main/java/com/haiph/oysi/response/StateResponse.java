package com.haiph.oysi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.haiph.oysi.model.state.State;

import java.util.ArrayList;
import java.util.List;

public class StateResponse extends BaseResponse{
    @SerializedName("data")
    @Expose
    public List<State> data = new ArrayList<>();
}
