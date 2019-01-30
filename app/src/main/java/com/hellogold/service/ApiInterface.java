package com.hellogold.service;


import com.hellogold.model.SpotPrice;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by SARINRATH on 30/01/2019 AD.
 */

public interface ApiInterface {

    @GET("/api/v2/spot_price.json")
    Call<SpotPrice> spot_price();

    @FormUrlEncoded
    @POST("/api/v3/users/register.json")
    Call<String> register(
            @Field("email") String email,
            @Field("uuid") String uuid,
            @Field("data") String data,
            @Field("tnc") String tnc
    );
}
