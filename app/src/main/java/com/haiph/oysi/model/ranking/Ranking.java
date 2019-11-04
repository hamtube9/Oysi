
package com.haiph.oysi.model.ranking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ranking {
//ong hiu chua :)
    //oh rồi . thế thì lấy list datum đúng k/ uk
    @SerializedName("current_aqi")
    @Expose
    private Integer currentAqi;
    @SerializedName("current_aqi_cn")
    @Expose
    private Integer currentAqiCn;
    @SerializedName("updated")
    @Expose
    private String updated;

    public Integer getCurrentAqi() {
        return currentAqi;
    }

    public void setCurrentAqi(Integer currentAqi) {
        this.currentAqi = currentAqi;
    }

    public Integer getCurrentAqiCn() {
        return currentAqiCn;
    }

    public void setCurrentAqiCn(Integer currentAqiCn) {
        this.currentAqiCn = currentAqiCn;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

}
