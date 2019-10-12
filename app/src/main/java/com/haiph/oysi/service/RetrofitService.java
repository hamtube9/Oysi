package com.haiph.oysi.service;

import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    public static Service service;

    public static Service getInstance(){

        if (service == null){
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("http://api.airvisual.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service  =  retrofit.create(Service.class);
        }

        return  service;
    }
}
