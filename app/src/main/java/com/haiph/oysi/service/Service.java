package com.haiph.oysi.service;

import com.haiph.oysi.response.CityResponse;
import com.haiph.oysi.response.CurrentLocationRespone;
import com.haiph.oysi.response.LittleCityResponse;
import com.haiph.oysi.response.StateResponse;
import com.haiph.oysi.response.CountryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {


    //http://api.airvisual.com/v2/countries?key=643d17a2-2def-469d-8c9b-bd90c5a7a550

    @GET("/v2/countries")
    Call<CountryResponse> getAllCountry(@Query("key") String key);

    //curl --location --request GET "api.airvisual.com/v2/states?country=USA&key={{YOUR_API_KEY}}"

    @GET("/v2/states")
    Call<StateResponse> getAllState(@Query("country") String country, @Query("key") String key);

    //curl --location --request GET "api.airvisual.com/v2/cities?state=New%20York&country=USA&key={{YOUR_API_KEY}}"
    @GET("/v2/cities")
    Call<CityResponse> getAllCity(@Query("state") String state,
                                  @Query("country") String country,
                                  @Query("key") String key);

    //api.airvisual.com/v2/nearest_city?lat=21.031263&lon=105.8509413&key=643d17a2-2def-469d-8c9b-bd90c5a7a550

    @GET("/v2/nearest_city")
    Call<CurrentLocationRespone> getCurrentLocation(@Query("lat") String lat,
                                                    @Query("lon") String lon,
                                                    @Query("key") String key);

    //api.airvisual.com/v2/city_ranking?key={{YOUR_API_KEY}}

    //api.airvisual.com/v2/city?city={{CITY_NAME}}&state={{STATE_NAME}}&country={{COUNTRY_NAME}}&key={{YOUR_API_KEY}}

    @GET("/v2/city")
    Call<LittleCityResponse> getLittleCity(@Query("city") String city,
                                           @Query("state") String state,
                                           @Query("country") String country,
                                           @Query("key") String key);

}
