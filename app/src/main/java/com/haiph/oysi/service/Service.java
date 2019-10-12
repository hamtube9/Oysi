package com.haiph.oysi.service;

import com.haiph.oysi.model.country.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {


    //http://api.airvisual.com/v2/countries?key=643d17a2-2def-469d-8c9b-bd90c5a7a550

    @GET("/v2/countries")
    Call<List<Country>> getAllCountry(@Query("Key") String key);
}
