package com.haiph.oysi.service;


import com.haiph.oysi.response.RankingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestService {
//gate/Airvisual.php?nearest_city
    //http://khachhangonline.com/gate/Airvisual.php?city_ranking

    @GET("/gate/Airvisual.php?city_ranking")
    Call<RankingResponse> getRanking();

    //http://khachhangonline.com/nearest_city?lat=21.029469&lon=105.852175
//    @GET("/nearest_city")
//    Call<CityRequestResponse> getNearestCity(
//            @Query("lat") String lat,
//            @Query("lon") String lon);
}
