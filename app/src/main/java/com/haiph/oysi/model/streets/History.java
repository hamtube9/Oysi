
package com.haiph.oysi.model.streets;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("weather")
    @Expose
    private List<Weather_> weather = null;
    @SerializedName("pollution")
    @Expose
    private List<Object> pollution = null;

    public List<Weather_> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather_> weather) {
        this.weather = weather;
    }

    public List<Object> getPollution() {
        return pollution;
    }

    public void setPollution(List<Object> pollution) {
        this.pollution = pollution;
    }

}
