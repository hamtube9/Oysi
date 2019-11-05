
package com.haiph.oysi.model.streets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Streets {

//la cai o postman a o
    //ư dung r

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
