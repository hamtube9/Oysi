package com.haiph.oysi.service;

import retrofit2.converter.gson.GsonConverterFactory;

public class RequestRetrofit {
    public static RequestService service;

    public static RequestService getRequest(){

        if (service == null){
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("http://khachhangonline.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service  =  retrofit.create(RequestService.class);
        }

        return  service;
    }
}
