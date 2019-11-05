
package com.haiph.oysi.model.streets;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("forecasts")
    @Expose
    private List<Forecast> forecasts = null;
    @SerializedName("forecasts_daily")
    @Expose
    private List<ForecastsDaily> forecastsDaily = null;
    @SerializedName("current")
    @Expose
    private Current current;
    @SerializedName("history")
    @Expose
    private History history;
    @SerializedName("units")
    @Expose
    private Units units;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public List<ForecastsDaily> getForecastsDaily() {
        return forecastsDaily;
    }

    public void setForecastsDaily(List<ForecastsDaily> forecastsDaily) {
        this.forecastsDaily = forecastsDaily;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

}
